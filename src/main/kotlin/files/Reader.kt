package files

import bean.Point
import bean.Points
import java.nio.charset.StandardCharsets
import java.nio.file.Path

class Reader {
    companion object {
        fun read(path: Path): Points =
            Points(path.toFile().reader(StandardCharsets.UTF_8).use { fileReader ->
                fileReader.readLines()
            }.drop(1)
                .map { line ->
                    val (a, b) = line.split("\\s".toRegex()).filter { it.isNotBlank() }.map { it.toDouble() }
                    Point(a, b)
                })
    }
}