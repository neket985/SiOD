object Main {
    private val tree = SplayTree<Int>()
    @JvmStatic
    fun main(args: Array<String>) {
        println("Добро пожаловать в систему тестирования работы сплэй-дерева!")
        Environments.startDialog(tree)
    }
}