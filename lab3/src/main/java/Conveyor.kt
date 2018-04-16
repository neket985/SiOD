import java.util.*

data class Conveyor(val placeTime: Int, val escapeTime: Int) {

    constructor() : this(random.nextInt(3600), random.nextInt(3600))

    val places = mutableListOf<Place>()

    fun randomGeneratePlaces(placesCount: Int) {
        for (i in 0..placesCount - 1) {
            val worksTime = random.nextInt(3600)
            val redirectTime = random.nextInt(3600)
            places.add(Place(worksTime, redirectTime))
        }
    }

    companion object {
        private val random = Random()
    }
}