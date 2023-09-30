import entity.*
import utils.CsvParser
import java.io.File
import kotlin.math.pow

fun main(args: Array<String>) {
    launchAIDrinkTask()
}

fun launchAIDrinkTask() {
    //выберем список соседей
    val neighboursList = getNeighboursList()

    println("Введите количество рассматриваемых соседей: (до 5)")
    val neighboursCount = readln().toInt()

    //Введем изначальные данные и составим из них модель
    val initialModel = insertInitialModel()

    //Обработаем изначальные данные (подправим список соседей)
    val filteredNeighbours = getAndPrintNeighboursFilteredByInitialModel(neighboursList, initialModel, neighboursCount)

    //Выведем и напиток
    println(
        getFavorableDrink(filteredNeighbours, initialModel)
    )
}

private fun getNeighboursList(): List<Neighbour> = CsvParser().getNeighbours(File("answers.csv"))

private fun insertInitialModel(): Neighbour {
    println("Введите пол? М/Ж")
    val gender = Gender.from(readln())

    println("Занимаетесь спортом? да/нет")
    val isSportsman = when (readln()) {
        "да" -> true
        "нет" -> false
        else -> false
    }

    println("Есть ли работа? да/нет")
    val hasWork = when (readln()) {
        "да" -> true
        "нет" -> false
        else -> false
    }

    println("Есть ли сердечные заболевания? да/нет")
    val isHeartDisease = when (readln()) {
        "да" -> true
        "нет" -> false
        else -> false
    }

    println("Сова или Жаворонок")
    val sleepingType = SleepingType.from(readln())

    println("Есть ли молоко в холодильнике? да/нет")
    val hasMilk = when (readln()) {
        "да" -> true
        "нет" -> false
        else -> false
    }

    println("Время подъема")
    val wakingHour = readln().toInt()

    println(
        """
        |Административный округ
        |Введите один из списка:
        |1) Восточный административный округ
        |2) Подмосковье
        |3) Северный административный округ
        |4) Северо-Восточный административный округ
        |5) Северо-западный административный округ
        |6) Центральный административный округ
        |7) Юго-Восточный административный округ
        |8) Юго-Западный административный округ
        |9) Южный административный округ
    """.trimMargin()
    )
    val area = Area.from(readln())

    println("Время сна")
    val sleepingHour = readln().toInt()

    return Neighbour(
        drink = DrinkType.UNDEFINED,
        gender = gender,
        isSport = isSportsman,
        hasWork = hasWork,
        isHeartDisease = isHeartDisease,
        sleepingType = sleepingType,
        hasMilk = hasMilk,
        wakingHour = wakingHour,
        area = area,
        sleepingHour = sleepingHour
    )
}

private fun getAndPrintNeighboursFilteredByInitialModel(
    neighboursList: List<Neighbour>,
    initialModel: Neighbour,
    neighboursCount: Int
): List<Neighbour> {
    //Вощьмем первые X ближайших к нам соседей
    val newlist = neighboursList
        .sortedBy {
            initialModel.getAbsoluteGraphDistance(it)
        }
        .take(neighboursCount)
        .onEach {
            println(it.toString() + " Расстояние: ${initialModel.getAbsoluteGraphDistance(it)}")
        }

    return newlist.toList()
}

private fun getFavorableDrink(neighbours: List<Neighbour>, initialModel: Neighbour): DrinkType {

    //Посчитаем любителей кофе и чая
    val teaCount = neighbours.count { it.drink == DrinkType.TEA }
    val coffeeCount = neighbours.count { it.drink == DrinkType.COFFEE }

    //Сравним, если неясно любителей поровну - смотрим на другие параметры
    return when {
        teaCount > coffeeCount -> DrinkType.TEA
        teaCount < coffeeCount -> DrinkType.COFFEE
        else -> getDrinkInTie(neighbours, initialModel)
    }
}

private fun getDrinkInTie(neighbours: List<Neighbour>, initialModel: Neighbour): DrinkType {
    val teaAbsoluteDistanceSum =
        getSum(neighbours.filter { it.drink == DrinkType.TEA }.map { it.getAbsoluteGraphDistance(initialModel) })

    val coffeeAbsoluteDistanceSum =
        getSum(neighbours.filter { it.drink == DrinkType.COFFEE }.map { it.getAbsoluteGraphDistance(initialModel) })

    //Посортируем по убыванию по оставшимся критериям и достаем напиток
    return when (teaAbsoluteDistanceSum > coffeeAbsoluteDistanceSum) {
        true -> DrinkType.COFFEE
        false -> DrinkType.TEA
    }
}

private fun getSum(list: List<Double>): Double {
    return list.sumOf { it.pow(2) }
}