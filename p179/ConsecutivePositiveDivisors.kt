import kotlin.math.sqrt

fun main() {
  val n = 10_000_000
  val count = divisorsCount(n)
  val answer = count.toList().zipWithNext().count { it.first == it.second }
  println(answer)
}

private fun divisorsCount(upTo: Int): IntArray {
  val primeDivisors = getPrimeDivisors(upTo)
  val count = IntArray(upTo)
  count[1] = 1
  for (n in 2 until upTo) {
    val prime = primeDivisors[n]
    var nDivided = n
    var exponent = 0
    while (nDivided > 1 && nDivided % prime == 0) {
      nDivided /= prime
      exponent++
    }
    count[n] = (exponent + 1) * count[nDivided]
  }
  return count
}

private fun getPrimeDivisors(upTo: Int): IntArray {
  val divisors = IntArray(upTo) { it }
  for (p in 2 .. sqrt(upTo.toDouble()).toInt()) {
    if (divisors[p] == p) {
      for (d in p * p until upTo step p) {
        divisors[d] = p
      }
    }
  }
  return divisors
}
