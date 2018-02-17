import java.lang.StringBuilder

class MyList<T> {
    var head: Item<T>? = null

    fun add(index: Int, data: T) {
        findAndDoSmth(index) { prev, next ->
            val newItem = Item(data, next)
            if (prev == null) head = newItem
            else prev.next = newItem
        }
    }

    fun find(index: Int) =
            findAndDoSmth(index) { prev, now ->
                now
            }

    private fun <F> findAndDoSmth(index: Int, doSmth: (Item<T>?, Item<T>?) -> F): F {
        if (index == 0) {
            return doSmth(null, head)
        } else {
            var iter = head!!
            for (i in 0 until index - 1) {
                iter = iter.next!!
            }
            return doSmth(iter, iter.next)
        }
    }

    fun remove(data: T) {
        var iter = head!!
        if (iter.data!!.equals(data)) {
            head = head?.next
            iter.next = null
            return
        }
        while (iter.next != null) {
            if (iter.next!!.data!!.equals(data)) {
                val removed = iter.next!!
                iter.next = iter.next?.next
                removed.next = null
                break
            }
            iter = iter.next!!
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.appendln("Вывод элементов односвязанного списка:")

        var index = 0
        var iter = head
        while (iter != null) {
            sb.appendln("$index: ${iter.data.toString()}")
            iter = iter.next
            ++index
        }
        return sb.toString()
    }
}