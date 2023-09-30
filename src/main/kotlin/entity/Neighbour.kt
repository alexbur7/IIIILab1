package entity

import kotlin.math.pow
import kotlin.math.sqrt

data class Neighbour(
    val drink: DrinkType,
    val gender: Gender,
    val isSport: Boolean,
    val hasWork: Boolean,
    val isHeartDisease: Boolean,
    val sleepingType: SleepingType,
    val hasMilk: Boolean,
    val wakingHour: Int,
    val area: Area,
    val sleepingHour: Int,
) {

    fun getAbsoluteGraphDistance(otherNeighbour: Neighbour): Double = sqrt(
        (gender.value - otherNeighbour.gender.value).toDouble().pow(2) +
                (isSport.toInt() - otherNeighbour.isSport.toInt()).toDouble().pow(2) +
                (hasWork.toInt() - otherNeighbour.hasWork.toInt()).toDouble().pow(2) +
                (isHeartDisease.toInt() - otherNeighbour.isHeartDisease.toInt()).toDouble().pow(2) +
                (sleepingType.value - otherNeighbour.sleepingType.value).toDouble().pow(2) +
                (hasMilk.toInt() - otherNeighbour.hasMilk.toInt()).toDouble().pow(2) +
                (wakingHour - otherNeighbour.wakingHour).toDouble().pow(2) +
                (area.value - otherNeighbour.area.value).toDouble().pow(2) +
                (sleepingHour - otherNeighbour.sleepingHour).toDouble().pow(2)
    )

    private fun Boolean.toInt() = if (this) 1 else 0
}