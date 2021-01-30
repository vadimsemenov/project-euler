fun main() {
  val (width, height) = readLine()!!.split(' ').map { it.toInt() }

  val rows = arrayListOf<List<Int>>().apply {
    fun generateRows(prefix: List<Int>) {
      val currentWidth = prefix.lastOrNull() ?: 0
      if (currentWidth > width) return
      for (i in 2..3) {
        val nextWidth = currentWidth + i
        if (nextWidth == width) add(prefix)
        generateRows(prefix + nextWidth)
      }
    }
    generateRows(prefix = emptyList())
  }

  val graph = Array<List<Int>>(rows.size) {
    val prevRow = rows[it]
    arrayListOf<Int>().apply {
      outer@for ((nextIndex, nextRow) in rows.withIndex()) {
        var i = 0; var j = 0
        while (i < prevRow.size && j < nextRow.size) {
          if (prevRow[i] < nextRow[j]) i++
          else if (prevRow[i] > nextRow[j]) j++
          else continue@outer
        }
        add(nextIndex)
      }
    }
  }

  val dp = Array(2) {
    LongArray(rows.size) { 1 }
  }
  var cur = 0
  repeat(height - 1) {
    val next = cur xor 1
    dp[next].fill(0)
    for (i in rows.indices) {
      for (j in graph[i]) {
        dp[next][i] += dp[cur][j]
      }
    }
    cur = next
  }
  println(dp[cur].sum())
}

