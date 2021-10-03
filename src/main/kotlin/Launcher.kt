import bean.Matrix
import bean.Points
import files.Reader
import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.apache.commons.math3.linear.EigenDecomposition
import org.apache.commons.math3.linear.MatrixUtils
import java.nio.file.Path

val resources: Path = Path.of("src").resolve("main").resolve("resources")

fun main() {
    val points = Reader.read(resources.resolve("X.txt"))
    Painter.paint(resources.resolve("input.png"), 1500, points)
    var sigma = Matrix(2, 2)
    var sum = Matrix(2, 1)
    points.points.forEach {
        sum += it.toMatrix()
    }
    points.points.forEach {
        sigma += it.toMatrix() * it.toMatrix().transpose()
    }
    sum /= points.points.size.toDouble()
    sigma /= points.points.size.toDouble()
    val zeroPoints = points.points.map {
        it.toMatrix() - sum
    }
    Painter.paint(resources.resolve("zeroPoints.png"), 1500, Points(zeroPoints.map { it.toPoint() }))
    val sigmaReal = Array2DRowRealMatrix(sigma.matrix.map { it.toDoubleArray() }.toTypedArray())
    val decomposition = EigenDecomposition(sigmaReal)
    val a = decomposition.d
    val u = decomposition.v
    val inverseA = MatrixUtils.inverse(a)
    val transpose = u.transpose()
    val whiter = inverseA.multiply(transpose)
    val whiterMatrix = Matrix(whiter.data.map { it.toMutableList() }.toMutableList())
    val writerPoints = zeroPoints.map {
        whiterMatrix * it
    }.map {
        it.toPoint()
    }
    Painter.paint(resources.resolve("whiter.png"), 1500, Points(writerPoints))
}