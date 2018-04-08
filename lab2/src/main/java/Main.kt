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