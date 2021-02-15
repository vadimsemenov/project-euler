import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.max
import kotlin.math.min
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalTime
suspend fun main() {
  val answer = AtomicInteger(0)
  val runningTime = measureTime {
    coroutineScope {
      repeat(9 * 4 + 1) { sum ->
        launch {
          System.err.println("Calculating for sum = $sum")
          answer.addAndGet(solveForSum(sum))
          System.err.println("Done for sum = $sum")
        }
      }
    }
  }
  System.err.println("Done in $runningTime") // ~45s on my machine.
  println(answer)
}

// Warning: Quite slow :/
private fun solveForSum(sum: Int): Int {
  val size = 4
  val min = IntArray(size) { max(0, sum - 9 * (3 - it)) }
  val max = IntArray(size) { min((it + 1) * 9, sum) }
  val capacities = max.zip(min).map { it.first - it.second + 1 }
  val dp = Array(size) {
    val capacity = capacities[it]
    Array(capacity) { Array(capacity) { Array(capacity) { Array(capacity) { Array(capacity) { IntArray(capacity) } } } } }
  }
  val possibleRows = (0 until BigInteger.TEN.pow(size).intValueExact())
      .map {
        var n = it
        IntArray(size) {
          n % 10.also { n /= 10 }
        }
      }
      .filter { it.sum() == sum }

  for (row in 0 until size) {
    for (digits in possibleRows) {
      val (d0, d1, d2, d3) = digits
      if (row == 0) {
        dp[row][d0 - min[row]][d1 - min[row]][d2 - min[row]][d3 - min[row]][d0 - min[row]][d3 - min[row]]++
      } else {
        val prevRow = row - 1
        val delta = min[prevRow] - min[row]
        for (s0 in dp[prevRow].indices) {
          val ns0 = s0 + d0 + delta
          if (ns0 < 0 || ns0 >= capacities[row]) continue
          for (s1 in dp[prevRow][s0].indices) {
            val ns1 = s1 + d1 + delta
            if (ns1 < 0 || ns1 >= capacities[row]) continue
            for (s2 in dp[prevRow][s0][s1].indices) {
              val ns2 = s2 + d2 + delta
              if (ns2 < 0 || ns2 >= capacities[row]) continue
              for (s3 in dp[prevRow][s0][s1][s2].indices) {
                val ns3 = s3 + d3 + delta
                if (ns3 < 0 || ns3 >= capacities[row]) continue
                for (s4 in dp[prevRow][s0][s1][s2][s3].indices) {
                  val ns4 = s4 + digits[row] + delta
                  if (ns4 < 0 || ns4 >= capacities[row]) continue
                  for (s5 in dp[prevRow][s0][s1][s2][s3][s4].indices) {
                    val ns5 = s5 + digits[size - 1 - row] + delta
                    if (ns5 < 0 || ns5 >= capacities[row]) continue
                    dp[row][ns0][ns1][ns2][ns3][ns4][ns5] += dp[prevRow][s0][s1][s2][s3][s4][s5]
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  val idx = sum - min[size - 1]
  return dp[size - 1][idx][idx][idx][idx][idx][idx]
}
