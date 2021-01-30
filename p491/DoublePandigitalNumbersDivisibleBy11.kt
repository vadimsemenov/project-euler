fun main() {
  val powers = IntArray(11).apply {
    this[0] = 1
    for (i in 1 until size) {
      this[i] = 3 * this[i - 1]
    }
  }
  val dp = Array(11) {
    LongArray(powers.last())
  }
  dp[0][0] = 1
  for (mask in 0 until powers.last()) {
    for (reminder in 0..10) {
      for (digit in 0..9) {
        if (mask == 0 && digit == 0) continue
        if (mask / powers[digit] % 3 == 2) continue
        dp[(reminder * 10 + digit) % 11][mask + powers[digit]] += dp[reminder][mask]
      }
    }
  }
  println(dp[0][powers.last() - 1])
}