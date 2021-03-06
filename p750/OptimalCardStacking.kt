import kotlin.math.abs

fun main() {
  fun solve(n: Int): Int {
    val initialPosition = run {
      var power = 3
      IntArray(n).apply {
        repeat(n) {
          set(power - 1, it)
          power = power * 3 % (n + 1)
        }
        check(toSet().size == n) { "Not a permutation" }
      }
    }
    val dp = Array(n) { i ->
      IntArray(n) { if (it == i) 0 else Int.MAX_VALUE / 3 }
    }

    for (len in 1 .. n) {
      for (i in 0 .. n - len) {
        val j = i + len - 1
        for (k in i until j) {
          val cost = dp[i][k] + dp[k + 1][j] + abs(initialPosition[j] - initialPosition[k])
          if (cost < dp[i][j]) dp[i][j] = cost
        }
      }
    }
    return dp[0][n - 1]
  }

  println(solve(976))
}