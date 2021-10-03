package bean

data class Point(val x : Double, val y : Double) {
    fun toMatrix() : Matrix {
        return Matrix(mutableListOf(mutableListOf(x), mutableListOf(y)))
    }
}