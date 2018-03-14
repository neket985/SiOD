enum class Environments(val command: String, val dialog: (SplayTree<Int>) -> Boolean) {
    home("home", {
        println("Введите одну из доступных операций (add, find, delete, show, quit)")
        val read = readLine()
        val readedEnv = envByCommand[read]
        if (readedEnv == null) {
            println("Неподдерживаемая команда \"$read\"")
            currentEnv = home
        } else {
            currentEnv = readedEnv
        }
        true
    }),
    add("add", { tree ->
        println("Введите целое число для создания нового объекта")
        try {
            val value = readLine()!!.toInt()
            tree.add(value)
            println("Элемент успешно добавлен")
        } catch (e: NumberFormatException) {
            println("Неверный формат ответа")
        } catch (e: NullPointerException) {
            println("Элемент не может быть добавлен так как он уже присутствует в дереве")
        }
        currentEnv = home
        true
    }),
    find("find", { tree ->
        println("Введите целое число для поиска элемента")
        try {
            val value = readLine()!!.toInt()
            val founded = tree.find(value)
            println("Элемент найден")
        } catch (e: NumberFormatException) {
            println("Неверный формат ответа")
        } catch (e: NullPointerException) {
            println("Элемент не найден")
        }
        currentEnv = home
        true
    }),
    delete("delete", { tree ->
        println("Введите значение удаляемого объекта (целое число)")
        try {
            val value = readLine()!!.toInt()
            tree.delete(value)
            println("Элемент успешно удалён")
        } catch (e: NumberFormatException) {
            println("Неверный формат ответа")
        } catch (e: NullPointerException) {
            println("Элемент не найден")
        }
        currentEnv = home
        true
    }),
    show("show", { tree ->
        println(tree.toString())
        currentEnv = home
        true
    }),
    quit("quit", {
        println("До скорых встреч!")
        false
    });

    companion object {
        private val envByCommand = Environments.values().associateBy { it.command }
        private var currentEnv = Environments.home

        fun startDialog(tree: SplayTree<Int>) {
            while (currentEnv.dialog(tree)) {
            }
        }
    }
}