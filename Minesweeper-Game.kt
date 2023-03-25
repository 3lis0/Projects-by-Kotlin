package src.Minesweeper

import src.Minesweeper.GFG.Companion.floodFill
import kotlin.math.min
import kotlin.math.*

fun main(args: Array<String>) {
    val minefieldList = MutableList(9) {
        MutableList(9) {
            "."
        }
    }

    val fieldEdite = MutableList(9) {
        MutableList(9) {
            "."
        }
    }

    //print("How many mines do you want on the field? > ")
    val numMine = readln().toInt()
    printField(minefieldList)


    var xr = 0
    var yr = 0

    var checkStart = true
    do {
        print("Set/delete mine marks (x and y coordinates): ")
        val (x2, y2 , fm2) = readln().split(" ")
        xr = x2.toInt() - 1
        yr = y2.toInt() - 1

        if (fm2 == "mine") {
            if (fieldEdite[yr][xr] == "*"){
                fieldEdite[yr][xr] = "."
                printField(fieldEdite)
            } else {
                fieldEdite[yr][xr] = "*"
                printField(fieldEdite)
            }
        } else {
            checkStart = false
        }
    } while (checkStart)


    //createEmptyField(fieldEdite)

    //Make sure the first position in field is empty
    var emptyCell = true
    do {
        setMines(numMine,minefieldList)
        setNumbersOfMinesAround(minefieldList)
        if (minefieldList[yr][xr] == ".") {
            emptyCell = false
        } else {
            createEmptyField(minefieldList)
        }
    } while (emptyCell)


    val newField = copyOfField(minefieldList)
    floodFill(newField, yr,xr)

    for (x in 0 until 9) {
        for (y in 0 until 9) {
            if (newField[x][y] == "/"){
                fieldEdite[x][y] = "/"
                if (hasXAround(newField, x,y,".")){
                    floodFill(newField, x,y)
                }
            } else if (newField[x][y] != "X" && newField[x][y] != "."){
                if (hasXAround(newField, x,y,"/")){
                    fieldEdite[x][y] = newField[x][y]
                }
            }
        }
    }
    printField(fieldEdite)

    var check = false
    for (x in 0 until 9) {
        for (y in 0 until 9) {
            if(newField[x][y] != "X" && (fieldEdite[x][y] == "." || fieldEdite[x][y] == "*")){
                check = true
                break
            }
        }
    }

    if (check){
        setDeleteMark(newField,fieldEdite)
    } else {
        println("Congratulations! You found all the mines!")
    }

 }

fun createEmptyField(field: MutableList<MutableList<String>>) {
    for (x in 0 until 9) {
        for (y in 0 until 9) {
            field[x][y] = "."
        }
    }
}

fun copyOfField(field: MutableList<MutableList<String>>): MutableList<MutableList<String>>{
    val newField = MutableList(9) {
        MutableList(9) {
            "."
        }
    }
    for (x in 0 until 9) {
        for (y in 0 until 9) {
            newField[x][y] = field[x][y]
        }
    }
    return newField
}

fun setMines(num: Int, minefieldList: MutableList<MutableList<String>>){
    var xnum: Int = 0
    var ranx: Int = 0
    var rany: Int = 0
    if (num <= 81){
        while (xnum < num){
            ranx = (0.. 8).random()
            rany = (0.. 8).random()
            if (minefieldList[ranx][rany] != "X"){
                minefieldList[ranx][rany] = "X"
                xnum++;
            }
        }
    }
}

fun setNumbersOfMinesAround(minefieldList: MutableList<MutableList<String>>){
    var numx: Int = 0
    var valueX: Int = 0
    var valueY: Int = 0
    var count: Int = 0
    var allCalls: Int = 0
    var startX: Int = 0
    var startY: Int = 0
    var endX: Int = 0
    var endY: Int = 0
    for (x in 0 until 9) {
        for (y in 0 until 9) {
            if (minefieldList[x][y] != "X"){
                when {
                    x == 0 && y == 0 -> {
                        allCalls = 4
                        startX = x
                        startY = y
                        endX = x+1
                        endY = y+1
                    }
                    x == 0 && y == 8 -> {
                        allCalls = 4
                        startX = x
                        startY = y-1
                        endX = x+1
                        endY = y
                    }
                    x == 8 && y == 0 -> {
                        allCalls = 4
                        startX = x-1
                        startY = y
                        endX = x+1
                        endY = y+1
                    }
                    x == 8 && y == 8 -> {
                        allCalls = 4
                        startX = x-1
                        startY = y-1
                        endX = x
                        endY = y
                    }
                    x == 0 -> {
                        allCalls = 6
                        startX = x
                        startY = y-1
                        endX = x+1
                        endY = y+1
                    }
                    x == 8 -> {
                        allCalls = 6
                        startX = x-1
                        startY = y-1
                        endX = x
                        endY = y+1
                    }
                    y == 0 -> {
                        allCalls = 6
                        startX = x-1
                        startY = y
                        endX = x+1
                        endY = y+1
                    }
                    y == 8 -> {
                        allCalls = 6
                        startX = x-1
                        startY = y-1
                        endX = x+1
                        endY = y
                    }
                    else -> {
                        allCalls = 9
                        startX = x-1
                        startY = y-1
                        endX = x+1
                        endY = y+1
                    }
                }
                valueX = startX
                valueY = startY
                while (count < allCalls){
                    if (minefieldList[valueX][valueY] == "X"){
                        numx++
                    }
                    if (valueY == endY){
                        if (valueX != endX){
                            valueX++
                            valueY = startY
                        }
                    } else {
                        valueY++
                    }
                    count++
                }
                when (numx){
                    1 -> minefieldList[x][y] = "1"
                    2 -> minefieldList[x][y] = "2"
                    3 -> minefieldList[x][y] = "3"
                    4 -> minefieldList[x][y] = "4"
                    5 -> minefieldList[x][y] = "5"
                    6 -> minefieldList[x][y] = "6"
                    7 -> minefieldList[x][y] = "7"
                    8 -> minefieldList[x][y] = "8"
                }
                count = 0
                numx = 0
            } else {
                continue
            }
        }
    }
}


fun printField(minefieldList: MutableList<MutableList<String>>){
    println("""
         |123456789|
        -|---------|
    """.trimIndent())
    for (x in 0 until 9) {
        print("${x+1}|")
        for (y in 0 until 9) {
            print(minefieldList[x][y])
        }
        print("|")
        println()
    }
    println("-|---------|")
}


fun setDeleteMark(newField: MutableList<MutableList<String>>, fieldEdite: MutableList<MutableList<String>>) {
    print("Set/delete mine marks (x and y coordinates): ")
    val (x, y, fm) = readln().split(" ")
    val xr = x.toInt() - 1
    val yr = y.toInt() - 1

    if(fm == "mine" && fieldEdite[yr][xr] == "*"){
        fieldEdite[yr][xr] = "."
        printField(fieldEdite)
    } else if(fm == "mine") {
        fieldEdite[yr][xr] = "*"
        printField(fieldEdite)
    } else if (newField[yr][xr] == "X") {
        // show all mines and print:
        for (x1 in 0 until 9) {
            for (y1 in 0 until 9) {
                if (newField[x1][y1] == "X")
                {
                    fieldEdite[x1][y1] = newField[x1][y1]
                }
            }
        }
    } else if (newField[yr][xr] == ".") {
        floodFill(newField, yr,xr)
        //show empty cell and the number around
        for (x2 in 0 until 9) {
            for (y2 in 0 until 9) {
                if (newField[x2][y2] == "/"){
                    fieldEdite[x2][y2] = "/"
                    if (hasXAround(newField, x2,y2,".")){
                        floodFill(newField, x2,y2)
                    }
                } else if (newField[x2][y2] != "X" && newField[x2][y2] != "."){
                    if (hasXAround(newField, x2,y2, "/")){
                        fieldEdite[x2][y2] = newField[x2][y2]
                    }
                }
            }
        }
        printField(fieldEdite)
    } else if (newField[yr][xr] != "." && newField[yr][xr] != "X" && newField[yr][xr] != "/" && newField[yr][xr] != "*") {
        //show the number
        fieldEdite[yr][xr] = newField[yr][xr]
        printField(fieldEdite)
    } else if (fieldEdite[yr][xr] == "*"){
        fieldEdite[yr][xr] = "."
        printField(fieldEdite)
    }

    var check = false

   out@for (x3 in 0 until 9) {
        for (y3 in 0 until 9) {
            if (fieldEdite[yr][xr] == "X") {
                printField(fieldEdite)
                check = false
                break@out
            } else if(newField[x3][y3] != "X" && (fieldEdite[x3][y3] == "." || fieldEdite[x3][y3] == "*")){
                check = true
            }
        }
    }

    if (check) {
        setDeleteMark(newField,fieldEdite)
    } else if (fieldEdite[yr][xr] == "X"){
        println("You stepped on a mine and failed!")
    } else {
        println("Congratulations! You found all the mines!")
    }
}


class GFG {
    companion object {
        // Dimensions of paint screen
        private const val M = 9
        private const val N = 9

        // A recursive function to replace previous color 'prevC' at '(x, y)'
        // and all surrounding pixels of (x, y) with new color 'newC' and
        private fun floodFillUtil(screen: MutableList<MutableList<String>>, x: Int, y: Int, prevC: String, newC: String) {
            // Base cases
            if (x < 0 || x >= M || y < 0 || y >= N)
                return
            if (screen[x][y] != prevC)
                return

            // Replace the color at (x, y)
            screen[x][y] = newC

            // Recur for north, east, south and west
            floodFillUtil(screen, x + 1, y, prevC, newC)
            floodFillUtil(screen, x - 1, y, prevC, newC)
            floodFillUtil(screen, x, y + 1, prevC, newC)
            floodFillUtil(screen, x, y - 1, prevC, newC)
        }

        // It mainly finds the previous color on (x, y) and
        // calls floodFillUtil()
        fun floodFill(screen: MutableList<MutableList<String>>, x: Int, y: Int, newC: String = "/") {
            val prevC = screen[x][y]
            if (prevC == newC) return
            floodFillUtil(screen, x, y, prevC, newC)
        }
    }
}

fun hasXAround(arr: MutableList<MutableList<String>>, row: Int, col: Int, symbol: String): Boolean {
    if (row < 0 || col < 0 || row >= arr.size || col >= arr[0].size) {
        // The specified position is outside the bounds of the array
        return false
    }

    // Check the positions around the specified element for an "/" character
    for (i in max(0, row - 1)..min(row + 1, arr.size - 1)) {
        for (j in max(0, col - 1)..min(col + 1, arr[0].size - 1)) {
            if (i != row || j != col) {  // ignore the current element
                if (arr[i][j] == symbol) {
                    return true
                }
            }
        }
    }

    // No "/" character found around the specified element
    return false
}
