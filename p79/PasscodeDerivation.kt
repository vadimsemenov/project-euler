fun main() {
  val keylog = arrayListOf<MutableList<Int>>().apply {
    while (true) {
      val subsequence = readLine() ?: break
      add(IntArray(subsequence.length) { subsequence[it] - '0' }.toMutableList())
    }
  }

  val digits = (0..9)
      .filter { digit -> keylog.any { digit in it } }
      .toMutableList()

  val prefix = buildString {
    outer@while (true) {
      for (digit in digits) {
        if (keylog.all { it.indexOf(digit) <= 0 }) {
          append(digit)
          keylog.forEach { it.remove(digit) }
          keylog.removeIf { it.isEmpty() }
          digits.remove(digit)
          continue@outer
        }
      }
      break
    }
  }

  val remaining = buildString {
    repeat(digits.size) {
      append('?')
    }
  }

  println("$prefix$remaining")
}