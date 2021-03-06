fun main() {
  val upTo = 18
  val dp = Array(upTo + 1) { emptyList<Fraction>() }
  dp[1] = listOf(Fraction.ONE)
  val all = mutableSetOf(Fraction.ONE)
  for (n in 2 .. upTo) {
    val set = mutableSetOf<Fraction>()
    for (x in 1 .. n / 2) {
      val y = n - x
      for (a in dp[x]) {
        for (b in dp[y]) {
          set += a + b
          set += (a.reciprocal() + b.reciprocal()).reciprocal()
        }
      }
    }
    dp[n] = (set - all).toList()
    all.addAll(dp[n])
    System.err.println("Done $n -> ${all.size}")
  }
  println(all.size)
}

private data class Fraction(val numerator: Long, val denominator: Long) {
  init {
    check(gcd(numerator, denominator) == 1L)
  }

  operator fun plus(other: Fraction): Fraction {
    val lcm = this.denominator / gcd(this.denominator, other.denominator) * other.denominator
    val num = lcm / this.denominator * this.numerator + lcm / other.denominator * other.numerator
    val gcd = gcd(num, lcm)
    return Fraction(numerator = num / gcd, denominator = lcm / gcd)
  }

  operator fun times(other: Fraction): Fraction {
    val gcd1 = gcd(this.numerator, other.denominator)
    val gcd2 = gcd(this.denominator, other.numerator)
    return Fraction(
        numerator = (this.numerator / gcd1) * (other.numerator / gcd2),
        denominator = (this.denominator / gcd2) * (other.denominator / gcd1)
    )
  }

  fun reciprocal(): Fraction = Fraction(denominator, numerator)

  companion object {
    val ONE = Fraction(1L, 1L)

    private tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
  }
}

