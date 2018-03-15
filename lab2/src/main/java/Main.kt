import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import guru.nidi.graphviz.model.Factory
import guru.nidi.graphviz.model.Factory.graph
import guru.nidi.graphviz.model.LinkSource
import guru.nidi.graphviz.model.MutableNode
import java.io.File

object Main {
    private val tree = SplayTree<Int>()
    @JvmStatic
    fun main(args: Array<String>) {
        println("Добро пожаловать в систему тестирования работы сплэй-дерева!")
        tree.add(3)
        tree.add(5)
        tree.add(7)
        tree.add(9)
        tree.add(1)
        tree.add(6)
        Environments.startDialog(tree)
    }
}