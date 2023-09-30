package entity

enum class DrinkType(val russianName: String) {
    TEA("Чай"),
    COFFEE("Кофе"),
    UNDEFINED("");

    companion object {
        fun from(value: String?): DrinkType {
            return values().find { it.russianName == value } ?: UNDEFINED
        }
    }
}