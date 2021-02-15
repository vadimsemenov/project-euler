import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.math.abs

fun main() {
  BufferedReader(InputStreamReader(URL("https://projecteuler.net/project/resources/p102_triangles.txt").openStream())).use { reader ->
    var answer = 0
    while (true) {
      val line = reader.readLine() ?: break
      val vertices = line.split(",")
          .map { it.toInt() }
          .zipWithNext { x, y -> Point(x, y) }
          .filterIndexed { index, _ -> index % 2 == 0 }
      val totalArea = (vertices[1] - vertices[0]) * (vertices[2] - vertices[0])
      val partsArea = vertices[0] * vertices[1] + vertices[0] * vertices[2] + vertices[1] * vertices[2]
      if (totalArea == partsArea) {
        answer++
      }
    }
    println(answer)
  }
}

private data class Point(val x: Int, val y: Int) {
  operator fun minus(other: Point): Point = Point(this.x - other.x, this.y - other.y)
  operator fun times(other: Point): Int = abs(this.x * other.y - this.y * other.x)
}