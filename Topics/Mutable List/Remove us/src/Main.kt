import java.io.File

fun solution(elements: MutableList<String>, index: Int): MutableList<String> {
    elements.removeAt(index)
    return elements
}
fun main(){
    val num = 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1
    println(num) // it prints 0.9999999999999999
}