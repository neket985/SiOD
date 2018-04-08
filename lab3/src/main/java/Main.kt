import java.util.*

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        print("Введите количество рабочих мест на конвейере: ")
        val num = readLine()!!.toInt()

        val random = Random()
        val conveyor1 = Conveyor(random.nextInt(3600), random.nextInt(3600))
        conveyor1.randomGeneratePlaces(num)
        val conveyor2 = Conveyor(random.nextInt(3600), random.nextInt(3600))
        conveyor2.randomGeneratePlaces(num)

        val startTime = System.currentTimeMillis()
        val min = bruteForceCalculate(conveyor1, conveyor2)
        val time = System.currentTimeMillis() - startTime
        println("Done for ${time} millis")
    }

    private fun bruteForceCalculate(conveyor1: Conveyor, conveyor2: Conveyor): Pair<List<Boolean>, Long> {
        var minValue = Long.MAX_VALUE
        var minPath = emptyList<Boolean>()
        if (conveyor1.places.size != conveyor2.places.size) {
            throw Exception("Conveyors are not comparable")
        }
        val n = conveyor1.places.size
        val twoPowN = Math.pow(2.toDouble(), n.toDouble()).toLong()
        for (i in 0..twoPowN - 1) {
            val path = i.toBinaryArray(n)
            var resultTime = (if (path.first()) conveyor2.placeTime else conveyor1.placeTime).toLong()  //добавляем время постановки на ковейер
            for (iter in 0..path.size-1) {
                if (iter != 0) { // добавляем время перенаправления ленты на другой конвейер
                    if (path[iter - 1] != path[iter]) {
                        resultTime += (if (path[iter - 1]) conveyor2 else conveyor1).places[iter - 1].redirectTime
                    }
                }

                val choosedConveyor = if (path[iter]) conveyor2 else conveyor1
                resultTime += choosedConveyor.places[iter].worksTime
            }
            resultTime += if (path.last()) conveyor2.escapeTime else conveyor1.escapeTime

            if (resultTime < minValue) {
                minValue = resultTime
                minPath = path
            }
        }
        return minPath to minValue
    }

    private fun Long.toBinaryArray(count: Int): List<Boolean> {
        val resultArray = mutableListOf<Boolean>()
        var a = this
        while (a != 0L) {
            resultArray.add(a % 2 == 1L)
            a = a / 2
        }
        for (i in 0..count - resultArray.size - 1) {
            resultArray.add(false)
        }
        return resultArray.asReversed()
    }
}