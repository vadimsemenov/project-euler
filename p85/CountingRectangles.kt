import kotlin.math.abs
import kotlin.math.sqrt

fun main() {
  val target = 2_000_000
  val sqrt = sqrt(target.toDouble()).toInt()
  var bestDiff = Long.MAX_VALUE
  var area = -1
  for (w in 1 .. sqrt) {
    for (h in 1 .. sqrt) {
      val n = w.toLong() * (w + 1) / 2 * h * (h + 1) / 2
      val diff = abs(target - n)
      if (diff < bestDiff) {
        bestDiff = diff
        area = w * h
        System.err.println("$w x $h -> $n")
      }
    }
  }
  println(area)
}