object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        while (true) {
            print("Введите количество рабочих мест на конвейере: ")
            val num = readLine()!!.toInt()

            val (conveyor1, conveyor2) = handleTimeOfExecuting({
                val conveyor1 = Conveyor()
                conveyor1.randomGeneratePlaces(num)
                val conveyor2 = Conveyor()
                conveyor2.randomGeneratePlaces(num)
                conveyor1 to conveyor2
            }, { time ->
                println("Conveyors created for ${time} millis")
            })

            val min1 = handleTimeOfExecuting({
                bruteForceCalculate(conveyor1, conveyor2)
            }, { time ->
                println("Brute force calculate done for ${time} millis")
            })

            val min2 = handleTimeOfExecuting({
                dinamicCalculate(conveyor1, conveyor2)
            }, { time ->
                println("Dinamic calculate done for ${time} millis")
            })

            println("Calculates sum equals: ${min1.second == min2.second}")
            println("Calculates paths equals: ${min1.first.equals(min2.first)}")
        }
    }

    private fun <T> handleTimeOfExecuting(hardWork: () -> T, handleTime: (Long) -> Unit): T {
        val startTime = System.currentTimeMillis()
        val result = hardWork()
        val time = System.currentTimeMillis() - startTime
        handleTime(time)
        return result
    }

    private fun dinamicCalculate(conveyor1: Conveyor, conveyor2: Conveyor): Pair<List<Boolean>, Int> {
        val conveyors = arrayOf(conveyor1, conveyor2)
        val mapOfPath = arrayOf<MutableList<DynamicEntity>>(mutableListOf(), mutableListOf())
        if (conveyor1.places.size != conveyor2.places.size) {
            throw Exception("Conveyors are not comparable")
        }
        val n = conveyor1.places.size
        for (i in 0..n - 1) {
            for (j in 0..1) {
                if (i == 0) { //первый элемент
                    val sum = conveyors[j].let { it.placeTime + it.places[i].worksTime }
                    mapOfPath[j].add(DynamicEntity(null, j, sum))
                } else {
                    val anotherConveyourNum = (j + 1) % 2
                    val sumFromCurrent = mapOfPath[j][i - 1].sum + conveyors[j].places[i].worksTime
                    val sumFromAbove = mapOfPath[anotherConveyourNum][i - 1].sum +
                            conveyors[anotherConveyourNum].places[i - 1].redirectTime +
                            conveyors[j].places[i].worksTime
                    if (sumFromAbove < sumFromCurrent) {
                        val sum =
                                if (i == n - 1) sumFromAbove + conveyors[j].escapeTime //последний элемент
                                else sumFromAbove

                        mapOfPath[j].add(DynamicEntity(mapOfPath[anotherConveyourNum][i - 1], j, sum))
                    } else {
                        val sum =
                                if (i == n - 1) sumFromCurrent + conveyors[j].escapeTime
                                else sumFromCurrent
                        mapOfPath[j].add(DynamicEntity(mapOfPath[j][i - 1], j, sum))
                    }
                }
            }
        }

        val startEntity =
                if (mapOfPath[0].last().sum < mapOfPath[1].last().sum) {
                    mapOfPath[0].last()
                } else {
                    mapOfPath[1].last()
                }
        return getPath(startEntity) to startEntity.sum
    }

    private fun getPath(start: DynamicEntity): List<Boolean> {
        val list = mutableListOf<Boolean>()
        var iter: DynamicEntity? = start
        while (iter != null) {
            list.add(iter.numOfConveyor == 1)
            iter = iter.previous
        }
        return list.asReversed()
    }

    private fun bruteForceCalculate(conveyor1: Conveyor, conveyor2: Conveyor): Pair<List<Boolean>, Int> {
        var minValue = Int.MAX_VALUE
        var minPath = emptyList<Boolean>()
        if (conveyor1.places.size != conveyor2.places.size) {
            throw Exception("Conveyors are not comparable")
        }
        val n = conveyor1.places.size
        val twoPowN = Math.pow(2.toDouble(), n.toDouble()).toLong()
        for (i in 0..twoPowN - 1) {
            val path = i.toBinaryArray(n)
            var resultTime = if (path.first()) conveyor2.placeTime else conveyor1.placeTime  //добавляем время постановки на ковейер
            for (iter in 0..path.size - 1) {
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