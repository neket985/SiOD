enum class Environments(val command: String, val dialog: (MyList<Data>) -> Boolean) {
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
    add("add", { list ->
        println("Введите порядковый номер нового объекта в списке (целое число)")
        try {
            val index = readLine()!!.toInt()
            println("Введите ключ для нового объекта (целое число)")
            val key = readLine()!!.toInt()
            println("Введите данные для нового объекта (строка)")
            val data = readLine()!!
            list.add(index, Data(key, data))
            println("Элемент успешно добавлен")
        } catch (e: NumberFormatException) {
            println("Неверный формат ответа")
        } catch (e: NullPointerException) {
            println("Элемент не может быть добавлен с указанным индексом")
        }
        currentEnv = home
        true
    }),
    find("find", { list ->
        println("Введите порядковый номер искомого объекта в списке (целое число)")
        try {
            val index = readLine()!!.toInt()
            val obj = list.find(index)!!.data
            println("Найденный элемент: ${obj}")
        } catch (e: NullPointerException) {
            println("Элемент с указанным индексом не найден")
        }
        currentEnv = home
        true
    }),
    delete("delete", { list ->
        println("Введите ключ удаляемого объекта (целое число)")
        try {
            val key = readLine()!!.toInt()
            println("Введите значение удаляемого объекта (строка)")
            val data = readLine()!!
            list.remove(Data(key, data))
            println("Элемент успешно удалён")
        } catch (e: NumberFormatException) {
            println("Неверный формат ответа")
        } catch (e: NullPointerException) {
            println("Элемент не найден")
        }
        currentEnv = home
        true
    }),
    show("show", { list ->
        println(list.toString())
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

        fun startDialog(list: MyList<Data>) {
            while (currentEnv.dialog(list)) {
            }
        }
    }
}