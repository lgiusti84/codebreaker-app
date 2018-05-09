package exam.luisgiusti.magnetocodebreaker.model

data class Stats(val mutantCount:Long = 0, val humanCount:Long = 0) {

    fun mutantPercent() = when {
        (humanCount + mutantCount) == 0L -> 50
        else -> ((100 * mutantCount) / (humanCount + mutantCount)).toInt()
    }
}