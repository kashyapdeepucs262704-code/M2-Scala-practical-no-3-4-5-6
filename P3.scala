
object FrequencyDistribution {

  def main(args: Array[String]): Unit = {

    val data = List(
      10, 20, 10, 30, 20, 40, 10, 50, 30, 20,
      40, 10, 30, 50, 20, 40, 30, 10, 50, 20
    )

    val frequency = data.groupBy(identity)
      .map { case (value, items) => value -> items.size }

    val sortedFrequency = frequency.toSeq.sortBy(_._1)

    var cumulativeFrequency = 0

    println("========== DATASET ==========")
    println(data.mkString(", "))

    println("\n===== FREQUENCY DISTRIBUTION =====")

    println(
      "%10s %12s %24s".format(
        "Value",
        "Frequency",
        "Cumulative Frequency"
      )
    )

    println("-" * 48)

    for ((value, count) <- sortedFrequency) {
      cumulativeFrequency += count

      println(
        "%10d %12d %24d".format(
          value,
          count,
          cumulativeFrequency
        )
      )
    }

    println("-" * 48)
    println("Total observations: " + data.size)
  }
}
