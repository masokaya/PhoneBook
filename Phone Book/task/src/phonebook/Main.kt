package phonebook

import java.io.File
import java.util.Hashtable

const val fileName = "C:\\Users\\Masokaya\\Downloads\\directory.txt"
const val findList = "C:\\Users\\Masokaya\\Downloads\\find.txt"
var timeTakenForLinear = 0L
var timeTakenForBubble = 0L
var timeTakenForQuick = 0L
var timeToCreateTable = 0L
fun main() {
    val findNames = File(findList).readLines()
    val names = File(fileName).readLines()
    println("Start searching (linear search)...")
    search(names, findNames)
    println("Start searching (bubble sort + jump search)...")
    bubbleSort(dir = names, findNames)
    println("Start searching (quick sort + binary search)...")
    quickSort(names, findNames)
    println("Start searching (hash table)...")
    val table = createHashtable(names)
    searchHashTable(table, findNames)
}

fun createHashtable(dir: List<String>): Hashtable<String, String> {
    val table = Hashtable<String, String>(dir.size + 20)
    val time1 = System.currentTimeMillis()
    for (item in dir) {
        table[hashSetFunction(item)] = item
    }
    val timer = System.currentTimeMillis()
    timeToCreateTable = (timer - time1)
    return table
}

fun searchHashTable(table: Hashtable<String, String>, find: List<String>) {
    var no = 0
    val time1 = System.currentTimeMillis()
    find.forEach {
        if (table[searchHashKey(it)] != null) {
            no += 1
        }
    }
    val timer = System.currentTimeMillis()
    val timeTaken = timer - time1
    val sec = timeTaken.toInt() / 1000
    val min = sec / 60
    // total time for creating hash table
    val sect = timeToCreateTable / 1000
    val mint = sect / 60
    val mst = timeToCreateTable - sect * 1000
    // total time for creating and searching hash table
    val secT = (timeToCreateTable + timeTaken).toInt() / 1000
    val minT = secT / 60
    val ms = (timeToCreateTable + timeTaken) - secT * 1000
    println("Found $no / ${find.count()} entries. Time taken: $minT min. $secT sec. $ms ms.")
    println("Creating time: $mint min. $sect sec. $mst ms.")
    println("Searching time: $min min. $sec sec. ${timeTaken - sec * 1000} ms.")
}

fun searchHashKey(name: String): String {
    val list = name.split(" ").toMutableList()
    return list.joinToString("")
}

fun hashSetFunction(name: String): String {
    val list = name.split(" ").toMutableList()
    list.removeFirst()
    return list.joinToString("")
}

fun search(dir: List<String>, find: List<String>, jumped: Boolean? = null) {
    val initial = System.currentTimeMillis()
    var no = 0
    for (item in find) {
        for (i in dir) {
            if (i.contains(item)) {
                no += 1
                break
            }
        }
    }
    val timer = System.currentTimeMillis()
    timeTakenForLinear = (timer - initial)
    val sec = timeTakenForLinear.toInt() / 1000
    val min = sec / 60
    when (jumped) {
        true -> {
            val timeTaken = (timeTakenForLinear + timeTakenForBubble).toInt()
            val seco = timeTaken / 1000
            val minu = sec / 60
            println("Found $no / ${find.count()} entries. Time taken: $minu min. ${seco - minu * 60} sec. ${timeTaken - seco * 1000} ms.")
        }
        false -> {
            val timeTaken = (timeTakenForLinear + timeTakenForQuick).toInt()
            val seco = timeTaken / 1000
            val minu = sec / 60
            println("Found $no / ${find.count()} entries. Time taken: $minu min. ${seco - minu * 60} sec. ${timeTaken - seco * 1000} ms.")
        }
        else -> {
            println("Found $no / ${find.count()} entries. Time taken: $min min. ${sec - min * 60} sec. ${timeTakenForLinear - (sec - min * 60) * 1000} ms.")
        }
    }
}

fun bubbleSort(dir: List<String>, find: List<String>) {
    val lis = dir.toMutableList()
    val time1 = System.currentTimeMillis()
    var s = false
    while (!s) {
        for ((first, second) in lis.asSequence().zipWithNext()) {
            val m1 = first.split(" ")[1]
            val m2 = second.split(" ")[1]
            if (m2 > m1) {
                val swap = lis[lis.indexOf(second)]
                lis[lis.indexOf(second)] = lis[lis.indexOf(first)]
                lis[lis.indexOf(first)] = swap
            }
            val timer = System.currentTimeMillis()
            timeTakenForBubble = (timer - time1)
            if (timeTakenForBubble != 0L && timeTakenForBubble >= 2 * timeTakenForLinear) {
                val sec = timeTakenForBubble.toInt() / 1000
                val min = sec / 60
                search(dir, find, true)
                val seco = timeTakenForLinear.toInt() / 1000
                val minu = seco / 60
                println("Sorting time: $min min. ${sec - min * 60} sec. ${timeTakenForBubble - sec * 1000} ms. -")
                println("Searching time: $minu min. $seco sec. ${timeTakenForLinear - seco * 1000} ms.")
                s = true
                break
            } else continue
        }
    }
}

fun quickSort(dir: List<String>, find: List<String>) {
    val lis = dir.toMutableList()
    val time1 = System.currentTimeMillis()
    var re = mutableListOf<String>()
    var isSorted = false
    val pivotals = mutableListOf<String>()
    pivotals.add(lis.last())
    val pivotalsIndex = mutableMapOf<Int, String>()
    while (!isSorted) {
        for (pi in pivotals) {
            val timer = System.currentTimeMillis()
            timeTakenForQuick = (timer - time1)
            when {
                timeTakenForQuick >= 2 * timeTakenForLinear -> {
                    val sec = timeTakenForQuick.toInt() / 1000
                    val min = sec / 60
                    search(dir, find, false)
                    val seco = timeTakenForLinear.toInt() / 1000
                    val minu = seco / 60
                    println("Sorting time: $min min. ${sec - min * 60} sec. ${timeTakenForQuick - sec * 1000} ms. -")
                    println("Searching time: $minu min. ${seco - minu * 60} sec. ${timeTakenForLinear - seco * 1000} ms.")
                    isSorted = true
                    break
                }
            }
            if (pi in pivotalsIndex.values) {
                val s1 = re.subList(0, re.indexOf(pi) - 1)
                val s2 = re.subList(re.indexOf(pi) + 1, re.lastIndex)
                re = reorderElementAroundPivotal(s1.last(), s1).toMutableList()
                re.add(pi)
                re.addAll(reorderElementAroundPivotal(s2.last(), s2))
                pivotals.add(s1.last())
                pivotals.add(s2.last())
                pivotalsIndex[re.indexOf(s1.last())] = s1.last()
                pivotalsIndex[re.indexOf(s2.last())] = s2.last()
            } else {
                re = reorderElementAroundPivotal(pi, lis).toMutableList()
                pivotalsIndex[re.indexOf(pi)] = pi
            }
        }
    }
}

fun reorderElementAroundPivotal(pivotal: String, list: List<String>): List<String> {
    val reordered = mutableListOf<String>()
    reordered.add(pivotal)
    val time1 = System.currentTimeMillis()
    for (item in list) {
        if (item < pivotal) {
            reordered.add(reordered.indexOf(pivotal), item)
        } else reordered.add(item)
        val timer = System.currentTimeMillis()
        val timeTaken = (timer - time1)
        if (timeTaken > 2 * timeTakenForLinear) break
    }
    return reordered
}
