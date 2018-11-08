import java.util.*
import kotlin.collections.ArrayList

fun main(args : Array<String>) {
    val tree = Tree("адпоин за") // ааиздп но 27, адпоинз а 21, идаап нзо 21, адпоин за 20
    println(tree.treeToString())
    println(tree.steps)
}

class Node(var string: ArrayList<Char>,
           var rNode: Node? = null,
           var lNode: Node? = null,
           var upNode: Node? = null,
           var downNode: Node? = null,
           var parent: Node? = null)

class Field(private val destinationString: ArrayList<Char>) {
    private var cash = Vector<String>()
    var isDone = false

    private fun isFit(x: Int, y: Int) = x in 0..2 && y in 0..2

    fun commitStep(currString: ArrayList<Char>, newX: Int, newY: Int, root: Node?): Node? {
        val spaceX = currString.indexOf(' ') % 3 - 1
        val spaceY = currString.indexOf(' ') / 3

        return if(isFit(spaceX + newX, spaceY + newY)) {
            val tempString: ArrayList<Char> = currString
            tempString[(spaceY + newY) * 3 + spaceX + 1 + newX] = ' '.also { tempString[currString.indexOf(' ')] = tempString[(spaceY + newY) * 3 + spaceX + 1 + newX] }

            if(cash.contains(tempString.toString())) return null
            else if(tempString == destinationString) isDone = true
            cash.add(tempString.toString())
            Node(tempString, parent = root)
        } else null
    }
}

class Tree(initString: String) {
    private val tempArr = ArrayList<Char>()
    private val head = Node(arrayListOf())
    private val queue = LinkedList<Node>()
    private var field = Field(arrayListOf('д', 'и', 'a', 'п', 'а', 'з', 'о', 'н', ' '))

    var lastNode: Node? = head
    var treeString = Vector<String>()
    var steps: Int = 0

    init {
        for(item in initString) tempArr.add(item)
        head.string = tempArr
        queue.push(head)
        checkVariations()
        buildVector()
    }

    private fun checkVariations() {
        while(!queue.isEmpty()) {
            val temp: Node = queue.pollFirst()

            temp.rNode = field.commitStep(temp.string, 1, 0, temp)
            if (temp.rNode != null && !field.isDone) queue.push(temp.rNode)
            else if (field.isDone) {
                lastNode = temp.rNode
                break
            }

            temp.lNode = field.commitStep(temp.string, -1, 0, temp)
            if (temp.lNode != null && !field.isDone) queue.push(temp.lNode)
            else if (field.isDone) {
                lastNode = temp.lNode
                break
            }

            temp.upNode = field.commitStep(temp.string, 0, -1, temp)
            if (temp.upNode != null && !field.isDone) queue.push(temp.upNode)
            else if (field.isDone) {
                lastNode = temp.upNode
                break
            }

            temp.downNode = field.commitStep(temp.string, 0, 1, temp)
            if (temp.downNode != null && !field.isDone) queue.push(temp.downNode)
            else if (field.isDone) {
                lastNode = temp.downNode
                break
            }
        }
    }

    private fun buildVector() {
        var root = lastNode
        var counter = 0
        while(root != null) {
            var temp = ""
            for(item in root.string) temp += item
            treeString.add((counter++).toString() + " " + temp + "\n")
            root = root.parent
        }
        steps = treeString.size - 1
    }

    fun treeToString(): String {
        var temp = ""
        for(item in treeString) temp += item + "\n"
        return temp
    }
}