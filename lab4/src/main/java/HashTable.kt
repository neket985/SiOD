import java.util.*

class HashTable<T>(val size: Int, val hashFunc: (Int) -> Int) {
    val hashArray = Array<MutableList<KeyValue<Int, T>>>(size, { mutableListOf() })

    fun insert(keyValue: KeyValue<Int, T>): Boolean =
            handleArrayElement(keyValue.key) { list ->
                if (list.firstOrNull { it.key == keyValue.key } == null) {
                    list.add(keyValue)
                } else false
            }

    fun remove(key: Int) =
            handleArrayElement(key) { list ->
                list.removeIf { it.key == key }
            }

    fun find(key: Int) =
            handleArrayElement(key) { list ->
                list.firstOrNull { it.key == key }
            }

    fun print(): String {
        val sb = StringBuilder()
        var index = 1
        hashArray.forEach { list ->
            sb.appendln("$index)")
            list.forEach { item ->
                sb.appendln("\t${item.key} -> ${item.value}")
            }
            ++index
        }
        return sb.toString()
    }

    fun randomGenerate(count: Int, genValue: (Random) -> T) {
        for (i in 0..count - 1) {
            if (!this.insert(KeyValue(random.nextInt(), genValue(random)))) {
                this.insert(KeyValue(random.nextInt(), genValue(random)))
            }
        }
    }

    fun cloneTo(other: HashTable<T>) {
        this.hashArray.forEach { list ->
            list.forEach { item ->
                other.insert(KeyValue(item.key, item.value))
            }
        }
    }

    private fun <D> handleArrayElement(key: Int, handle: (MutableList<KeyValue<Int, T>>) -> D): D {
        val index = hashFunc(key) % size
        return handle(hashArray[index])
    }

    companion object {
        val random = Random()
    }
}