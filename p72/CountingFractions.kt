fun main() {
  check(solve(8) == 21L) { solve(8) }
  val n = readLine()!!.toInt()
  println(solve(n))
}

fun solve(n: Int): Long {
  val divisor = IntArray(n + 1) { it }.apply {
    var p = 2
    while (p * p <= n) {
      if (this[p] == p) {
        for (d in (p * p) .. n step p) {
          this[d] = p
        }
      }
      p++
    }
  }
  val phi = IntArray(n + 1).apply {
    this[1] = 1
    for (i in 2..n) {
      val p = divisor[i]
      var iDivided = i
      var power = 1
      do {
        power *= p
        iDivided /= p
      } while (iDivided % p == 0)
      this[i] = (power - power / p) * this[iDivided]
    }
  }
  return phi.map { it.toLong() }.sum() - 1
}
