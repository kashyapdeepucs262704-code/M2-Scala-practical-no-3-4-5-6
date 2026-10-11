
import breeze.linalg.{DenseMatrix, DenseVector, inv}

object LinearRegressionExample {

  def main(args: Array[String]): Unit = {

    val x = DenseVector(1.0, 2.0, 3.0, 4.0, 5.0)
    val y = DenseVector(3.2, 5.1, 7.0, 9.3, 11.2)

    println("===== ORIGINAL DATA =====")
    println(s"X values: $x")
    println(s"Y values: $y")

    val n = x.length
    val X = DenseMatrix.zeros[Double](n, 2)

    for (i <- 0 until n) {
      X(i, 0) = 1.0
      X(i, 1) = x(i)
    }

    val Xt = X.t
    val coefficients = inv(Xt * X) * Xt * y

    val intercept = coefficients(0)
    val slope = coefficients(1)

    println("\n===== LINEAR REGRESSION RESULTS =====")
    println(f"Intercept (c): $intercept%.4f")
    println(f"Slope (m):     $slope%.4f")

    val newX = 6.0
    val predictedY = intercept + slope * newX

    println(s"\nPrediction for X = $newX")
    println(f"Predicted Y: $predictedY%.4f")

    val meanY = y.data.sum / y.length.toDouble

    val ssTotal = y.data.map(v => (v - meanY) * (v - meanY)).sum

    val ssResidual = (0 until n).map { i =>
      val predicted = intercept + slope * x(i)
      val error = y(i) - predicted
      error * error
    }.sum

    val rSquared = 1.0 - ssResidual / ssTotal

    println(f"\nModel R-squared: $rSquared%.4f")
  }
}
