import bean.Point
import bean.Points
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.lang.Double.max
import java.lang.Double.min
import java.nio.file.Path
import javax.imageio.ImageIO

class Painter {
    companion object {

        private fun pointer(size : Int, pos: Pair<Int, Int>): Pair<Int, Int> {
            return Pair(pos.first, size - pos.second)
        }

        private fun point(graphics: Graphics, pos : Pair<Int, Int>) {
            graphics.fillOval(pos.first - 4, pos.second - 4, 8, 8)
        }

        fun paint(path : Path, size : Int, points: Points) {
            val image = BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
            var minX = points.points.minOf { it.x }
            var maxX = points.points.maxOf { it.x }
            var minY = points.points.minOf { it.y }
            var maxY = points.points.maxOf { it.y }
            minX = min(minX, minY)
            minY = min(minX, minY)
            maxX = max(maxX, maxY)
            maxY = max(maxX, maxY)
            var sizeX = maxX - minX
            var sizeY = maxY - minY
            minX -= sizeX / 10
            maxX += sizeX / 10
            minY -= sizeY / 10
            maxY += sizeY / 10
            sizeX = maxX - minX
            sizeY = maxY - minY

            val graphics = image.graphics
            graphics.color = Color.GRAY.brighter()
            graphics.fillRect(0, 0, size, size)
            graphics.color = Color.BLACK
            val converter : (Point) -> Pair<Int, Int> = {
                Pair(((it.x - minX) * size / sizeX).toInt(), ((it.y - minY) * size / sizeY).toInt())
            }
            val painter : (Point) -> Unit = {
                it.let(converter).apply {
                    pointer(size, this)
                }.let { it1 ->
                    point(graphics, it1)
                }
            }
            points.points.forEach(painter)
            graphics.color = Color.RED
            Point(0.0, 0.0).let(painter)
            graphics.color = Color.GREEN
            Point(1.0, 0.0).let(painter)
            Point(0.0, 1.0).let(painter)
            Point(-1.0, 0.0).let(painter)
            Point(0.0, -1.0).let(painter)
            save(path, image)
        }

        private fun save(path : Path, image: BufferedImage) {
            ImageIO.write(image, "PNG", path.toFile())
        }
    }
}