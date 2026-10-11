
import breeze.linalg._
import breeze.numerics.sigmoid
import breeze.optimize.{DiffFunction, LBFGS}
import breeze.stats.distributions.Rand

object LogisticRegressionExample {

  def main(args: Array[String]): Unit = {

    val numSamples = 200
    val half = numSamples / 2
    val numFeatures = 2

    val cluster1 = DenseMatrix.zeros[Double](half, numFeatures)
    val cluster2 = DenseMatrix.zeros[Double](half, numFeatures)

    for (i <- 0 until half) {
      cluster1(i, 0) = 2.0 + Rand.gaussian.draw()
      cluster1(i, 1) = 2.0 + Rand.gaussian.draw()

      cluster2(i, 0) = 5.0 + Rand.gaussian.draw()
      cluster2(i, 1) = 5.0 + Rand.gaussian.draw()
    }

    val xTrain = DenseMatrix.vertcat(cluster1, cluster2)

    val yTrain = DenseVector.vertcat(
      DenseVector.zeros[Double](half),
      DenseVector.ones[Double](half)
    )

    val X = DenseMatrix.ones[Double](numSamples, numFeatures + 1)

    for (i <- 0 until numSamples) {
      X(i, 1) = xTrain(i, 0)
      X(i, 2) = xTrain(i, 1)
    }

    println("===== GENERATED DATASET =====")
    println(s"Number of samples: ${xTrain.rows}")
    println(s"Number of features: ${xTrain.cols}")

    def objective(weights: DenseVector[Double]): Double = {
      val probabilities = sigmoid(X * weights)
      val epsilon = 1e-12
      var loss = 0.0

      for (i <- 0 until numSamples) {
        val p = math.max(
          epsilon,
          math.min(1.0 - epsilon, probabilities(i))
        )

        loss -= yTrain(i) * math.log(p) +
          (1.0 - yTrain(i)) * math.log(1.0 - p)
      }

      loss / numSamples.toDouble
    }

    def gradient(weights: DenseVector[Double]): DenseVector[Double] = {
      val probabilities = sigmoid(X * weights)
      val errors = probabilities - yTrain
      (X.t * errors) / numSamples.toDouble
    }

    val diffFunction = new DiffFunction[DenseVector[Double]] {
      override def calculate(
                              weights: DenseVector[Double]
                            ): (Double, DenseVector[Double]) = {
        (objective(weights), gradient(weights))
      }
    }

    val optimizer = new LBFGS[DenseVector[Double]](
      maxIter = 200,
      m = 7
    )

    println("\n===== MODEL TRAINING =====")

    val initialWeights = DenseVector.zeros[Double](numFeatures + 1)
    val trainedWeights = optimizer.minimize(diffFunction, initialWeights)

    println("\n===== TRAINED MODEL WEIGHTS =====")
    println(f"Intercept (w0): ${trainedWeights(0)}%.4f")
    println(f"Feature 1 (w1): ${trainedWeights(1)}%.4f")
    println(f"Feature 2 (w2): ${trainedWeights(2)}%.4f")

    def predict(x1: Double, x2: Double): Unit = {
      val point = DenseVector(1.0, x1, x2)
      val probability = sigmoid(trainedWeights dot point)
      val predictedClass = if (probability >= 0.5) 1 else 0

      println(s"\nPrediction for point ($x1, $x2):")
      println(f"Predicted probability: $probability%.4f")
      println(s"Predicted class: $predictedClass")
    }

    predict(1.5, 1.8)
    predict(5.5, 5.2)

    println("\n===== PROGRAM COMPLETED =====")
  }
}
