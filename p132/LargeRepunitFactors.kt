import kotlin.math.sqrt

fun main() {
  solve(10).let { check(it == 9414L) { "$it != 9414" } }
  System.err.println("Computing...")
  println(solve(1_000_000_000))
}

private fun solve(k: Int): Long {
  val upTo = 200_000
  val primes = run {
    val isPrime = BooleanArray(upTo) { true }
    isPrime.fill(false, toIndex = 2)
    for (p in 2..2 + sqrt(upTo.toDouble()).toInt()) {
      if (isPrime[p]) {
        for (d in p * p until upTo step p) {
          isPrime[d] = false
        }
      }
    }
    val primes = arrayListOf<Int>()
    for (p in 2 until upTo) if (isPrime[p]) primes.add(p)
    primes
  }

  val repunit = Repunit(k)
  var sum = 0L
  var cnt = 0
  var maxPrime = 0
  for (p in primes) {
    if (cnt >= 40) break
    if (repunit % p == 0) {
      maxPrime = p
      cnt++
      sum += p
    }
  }
  System.err.println("R($k); primes up to $upTo; max used prime = $maxPrime: $cnt -> $sum")
  return sum
}

private data class Repunit(val k: Int) {
  operator fun rem(prime: Int): Int {
    fun simpleRem(start: Int, ones: Int): Int {
      var cur = start
      repeat(ones) {
        cur = (cur * 10 + 1) % prime
      }
      return cur
    }

    val rest = simpleRem(0, k % (prime - 1))
    val repeatable = simpleRem(rest, (prime - 1) -  k % (prime - 1))
    return repeatable * (k / (prime - 1)) + rest
  }
}