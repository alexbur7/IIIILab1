package utils

import entity.*
import java.io.File

class CsvParser {

    fun getNeighbours(csv: File): List<Neighbour> {
        val neighbours = mutableListOf<Neighbour>()
        val BOM = "\uFEFF"
        val header = csv.useLines { it.firstOrNull()?.replace(BOM, "")?.split(",") }
            ?: throw Exception("This file does not contain a valid header")

        csv.useLines { linesSequence ->
            linesSequence
                .drop(1)
                .map { it.split(",") }
                .map { header.zip(it).toMap() }
                .forEach {
                    neighbours.add(map(it))
                }
        }
        return neighbours.toList()
    }

    private fun map(data: Map<String, String>): Neighbour {
        return Neighbour(
            drink = DrinkType.from(data[DRINK_KEY]),
            gender = Gender.from(data[GENDER_KEY]),
            isSport = data[SPORT_KEY].toBoolean(),
            hasWork = data[WORK_KEY].toBoolean(),
            isHeartDisease = data[HEART_KEY].toBoolean(),
            sleepingType = SleepingType.from(data[SLEEPING_KEY]),
            hasMilk = data[MILK_KEY].toBoolean(),
            wakingHour = data[WAKING_KEY].toInt(),
            area = Area.from(data[AREA_KEY]),
            sleepingHour = data[SLEEPING_HOUR_KEY].toInt()
        )
    }

    private fun String?.toInt(): Int {
        return when (this.orEmpty().find { it.isDigit() }) {
            '1' -> 1
            '2' -> 2
            '3' -> 3
            '4' -> 4
            '5' -> 5
            '6' -> 6
            '7' -> 7
            '8' -> 8
            '9' -> 9
            else -> 0
        }
    }

    private fun String?.toBoolean(): Boolean = when (this) {
        "Да" -> true
        "Нет" -> false
        else -> false
    }

    private companion object {
        const val DRINK_KEY = "Кофе или чай?"
        const val GENDER_KEY = "Пол"
        const val SPORT_KEY = "Занимаетесь спортом?"
        const val WORK_KEY = "Есть ли работа?"
        const val HEART_KEY = "Есть ли сердечные заболевания?"
        const val SLEEPING_KEY = "Сова или Жаворонок"
        const val MILK_KEY = "Есть ли молоко в холодильнике?"
        const val WAKING_KEY = "Время подъема"
        const val AREA_KEY = "Административный округ"
        const val SLEEPING_HOUR_KEY = "Время сна"
    }
}