package bean

data class Matrix(val matrix: MutableList<MutableList<Double>>) {

    constructor(n: Int, m: Int) : this(MutableList(n) { MutableList(m) { 0.0 } }) {}

    private val n = matrix.size
    private val m = matrix[0].size

    operator fun get(i: Int, j: Int): Double {
        return matrix[i][j]
    }

    operator fun set(i: Int, j: Int, a: Double) {
        matrix[i][j] = a
    }

    operator fun plus(another: Matrix): Matrix {
        check(this.n == another.n && this.m == another.m) { "Matrix size must be equals" }
        return Matrix((0 until n).map { i ->
            (0 until m).map { j ->
                this[i, j] + another[i, j]
            }.toMutableList()
        }.toMutableList())
    }

    operator fun minus(another: Matrix): Matrix {
        check(this.n == another.n && this.m == another.m) { "Matrix size must be equals" }
        return Matrix((0 until n).map { i ->
            (0 until m).map { j ->
                this[i, j] - another[i, j]
            }.toMutableList()
        }.toMutableList())
    }

    operator fun div(divided: Double): Matrix {
        return Matrix((0 until n).map { i ->
            (0 until m).map { j ->
                this[i, j] / divided
            }.toMutableList()
        }.toMutableList())
    }

    operator fun times(another: Matrix): Matrix {
        check(this.m == another.n) { "Matrix size wrong" }
        val newN = this.n
        val newM = another.m
        val matrix = Matrix(newN, newM)
        (0 until this.n).forEach { i ->
            (0 until another.m).forEach { j ->
                (0 until another.n).forEach { k ->
                    matrix[i, j] += this[i, k] * another[k, j]
                }
            }
        }
        return matrix
    }

    fun transpose(): Matrix {
        val matrix = Matrix(m, n)
        (0 until this.n).forEach { i ->
            (0 until this.m).forEach { j ->
                matrix[j, i] = this[i, j]
            }
        }
        return matrix
    }

    fun toPoint() : Point {
        check((n == 2 && m == 1) || (n == 1 && m == 1)) { "Can't be cast to Point" }
        if (n == 2) return Point(this[0, 0], this[1, 0])
        return Point(this[0, 0], this[0, 1])
    }

}