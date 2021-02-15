import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.math.min

fun main() {
  BufferedReader(InputStreamReader(URL("https://projecteuler.net/project/resources/p081_matrix.txt").openStream())).use { reader ->
    val table = arrayListOf<MutableList<Int>>().apply {
      while (true) {
        val line = reader.readLine() ?: break
        add(line.split(",").map { it.toInt() }.toMutableList())
      }
    }
    for (i in 1 until table.size) {
      table[i][0] += table[i - 1][0]
    }
    for (i in 1 until table[0].size) {
      table[0][i] += table[0][i - 1]
    }
    for (i in 1 until table.size) {
      for (j in 1 until table[i].size) {
        table[i][j] += min(table[i - 1][j], table[i][j - 1])
      }
    }
    println(table.last().last())
  }
}
