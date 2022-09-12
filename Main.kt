import java.util.Scanner
import java.util.*
fun printTable(list: MutableList<MutableList<Char>>){
    println("""
        ---------
        | ${list[0][0]} ${list[0][1]} ${list[0][2]} |
        | ${list[1][0]} ${list[1][1]} ${list[1][2]} |
        | ${list[2][0]} ${list[2][1]} ${list[2][2]} |
        ---------""")
}

fun checkCoordinate(list: MutableList<MutableList<Char>>): Unit {
    print("> ")
    val (x, y) = readln().split(" ")
    if (x.toIntOrNull()== null && y.toIntOrNull() == null){
        println("You should enter numbers!")
        checkCoordinate(list)
    } else if (x.toInt() > 3 || y.toInt() > 3) {
        println("Coordinates should be from 1 to 3!")
        checkCoordinate(list)
    } else if (list[x.toInt() - 1][y.toInt() - 1] == 'X' || list[x.toInt() - 1][y.toInt() - 1] == 'O'){
        println("This cell is occupied! Choose another one!")
        checkCoordinate(list)
    } else {
        addPlayer(list, x, y)
        printTable(list)
        winOrLose(list)
    }
}

fun addPlayer(list: MutableList<MutableList<Char>>, x: String, y: String){
    var Xc = 0
    var Oc = 0

    for (i in list.indices){
        for (x in list[i].indices){
            if (list[i][x] == 'X'){
                Xc++
            } else if (list[i][x] == 'O'){
                Oc++
            }
        }
    }

    if (Xc > Oc){
        list[x.toInt() - 1][y.toInt() - 1] = 'O'
    } else if (Xc == Oc){
        list[x.toInt() - 1][y.toInt() - 1] = 'X'
    }
}

fun winOrLose(input: MutableList<MutableList<Char>>){
    var Xs = ' '
    var  Os = ' '

    //rows
    if(input[0][0] == input[0][1] && input[0][1] == input[0][2]) {
        if (input[0][0] == 'X'){
            Xs = 'X'
        } else if (input[0][0] == 'O'){
            Os = 'O'
        }
    }
    if(input[1][0] == input[1][1] && input[1][1] == input[1][2]) {
        if (input[1][0] == 'X'){
            Xs = 'X'
        } else if (input[1][0] == 'O'){
            Os = 'O'
        }
    }
    if(input[2][0] == input[2][1] && input[2][1] == input[2][2]) {
        if (input[2][0] == 'X'){
            Xs = 'X'
        } else if (input[2][0] == 'O'){
            Os = 'O'
        }
    }

    //columns
    if(input[0][0] == input[1][0] && input[1][0] == input[2][0]) {
        if (input[0][0] == 'X'){
            Xs = 'X'
        } else if (input[0][0] == 'O'){
            Os = 'O'
        }
    }
    if(input[0][1] == input[1][1] && input[1][1] == input[2][1]) {
        if (input[0][1] == 'X'){
            Xs = 'X'
        } else if (input[0][1] == 'O'){
            Os = 'O'
        }
    }
    if(input[0][2] == input[1][2] && input[1][2] == input[2][2]) {
        if (input[0][2] == 'X'){
            Xs = 'X'
        } else if (input[0][2] == 'O'){
            Os = 'O'
        }
    }

    //X rows
    if(input[0][0] == input[1][1] && input[1][1] == input[2][2]) {
        if (input[0][0] == 'X'){
            Xs = 'X'
        } else if (input[0][0] == 'O'){
            Os = 'O'
        }
    }
    if(input[0][2] == input[1][1] && input[1][1] == input[2][0]) {
        if (input[0][2] == 'X'){
            Xs = 'X'
        } else if (input[0][2] == 'O'){
            Os = 'O'
        }
    }

    if (Xs == 'X') {
        println("X wins")
    } else if (Os == 'O') {
        println("O wins")
    } else if (!input.get(0).contains(' ') && !input.get(1).contains(' ') && !input.get(2).contains(' ')) {
        println("Draw")
    } else {
        checkCoordinate(input)
    }
}


fun main(args: Array<String>) {
    val list1: MutableList<Char> = mutableListOf()
    val list2: MutableList<Char> = mutableListOf()
    val list3: MutableList<Char> = mutableListOf()
    val listOfLists: MutableList<MutableList<Char>> = mutableListOf()


    val text = "         "

    for (x in 0 until text.length){
        if (x < 3){
            list1.add(text[x])
        } else if (x < 6){
            list2.add(text[x])
        } else if (x < 9){
            list3.add(text[x])
        }
    }
    listOfLists.add(list1)
    listOfLists.add(list2)
    listOfLists.add(list3)
    //print table
    printTable(listOfLists)
    //coordinate numbers
    winOrLose(listOfLists)
}
