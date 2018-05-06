object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val hashTable1 = HashTable<String>(100, { hash1(it) })
        hashTable1.randomGenerate(100) { it.nextLong().toString() }
        val hashTable2 = HashTable<String>(100, { hash2(it) })
        hashTable1.cloneTo(hashTable2)
        val hashTable3 = HashTable<String>(100, { hash3(it) })
        hashTable1.cloneTo(hashTable3)

        println("ht1:\n${hashTable1.print()}")
        println("ht2:\n${hashTable2.print()}")
        println("ht3:\n${hashTable3.print()}")
    }

    private fun hash1(key: Int) = Math.abs(Math.sqrt(key.toDouble()) * 1000).toInt()
    private fun hash2(key: Int) = Math.abs(Math.log(key.toDouble()) * 1000).toInt()
    private fun hash3(key: Int) = Math.abs(Math.cos(key.toDouble()) * Math.exp(key.toDouble())).toInt()
}