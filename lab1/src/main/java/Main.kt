import kotlin.io.*

object Main {
    private val list = MyList<Data>()
    @JvmStatic
    fun main(args: Array<String>) {
        println("Добро пожаловать в систему тестирования работы односвязанного списка!")
        Environments.startDialog(list)
    }
}