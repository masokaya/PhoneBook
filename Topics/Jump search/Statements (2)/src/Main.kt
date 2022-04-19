import kotlin.math.floor
import kotlin.math.sqrt

// You can experiment here, it won’t be checked

fun main(args: Array<String>) {
 val list = intArrayOf(1,-1,-32,-34,-22,-12,-22,4,3,6,22,44,23,54,66,21,121,2232,2,1,212,24,10,11,9,7,8,-11,-21,-45,-23)
    search1(list)
    //search(list)
}
fun search1(data: IntArray){
    val initial = System.currentTimeMillis()
    val st = floor(sqrt(data.size.toDouble())).toInt()
    val sorted =data.sorted()
    val block = MutableList(2){0}
    var result = 0
    for (i in 0 until data.count() step(st) ){
        if (i==0){
            when {
                sorted[i]==23 -> {
                    result= 0
                    break
                }
                sorted[st-1]==23 -> {
                    result=st-1
                    break
                }
                else ->{
                    block[0]=i
                }
            }

        }
        else {
            when {
                sorted[i]==23 -> {
                    result =i
                    break
                }
                sorted[i]>23 -> {
                    block[1]=i
                    break
                }
                sorted[i]<23 -> {
                    block[0]=i
                    continue
                }
                else -> continue
            }
        }
    }
   if (block[1]!=0){
       for (i in block[0]..block[1]){
           if (sorted[i]==23){
               result=i
               break
           }
       }
   }
    val timer = System.currentTimeMillis()
    val timeTaken = (timer - initial).toInt()
    val sec = timeTaken / 1000
    val min = sec / 60
    println("Found$result no / find.count()} entries. Time taken: $min min. ${sec - min * 60} sec. ${timeTaken - (sec - min * 60) * 1000} ms.")
}
fun search(intArray: IntArray) {
    val initial = System.currentTimeMillis()
    var no = 0
    val sorted =intArray.sorted()
    for (i in sorted) {
        if (i==23) {
            no =sorted.indexOf(i)
            break
        }
    }
    val timer = System.currentTimeMillis()
    val timeTaken = (timer - initial).toInt()
    val sec = timeTaken / 1000
    val min = sec / 60
    println("Found $no /find.count()} entries. Time taken: $min min. ${sec - min * 60} sec. ${timeTaken - (sec - min * 60) * 1000} ms.")
}