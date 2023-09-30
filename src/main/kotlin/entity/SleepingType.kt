package entity

enum class SleepingType(val value: Int, val russianName: String) {
    OWL(1, "Сова"),
    LARK(0, "Жаворонок");

    companion object {
        fun from(value: String?): SleepingType {
            return values().find { it.russianName == value } ?: OWL
        }
    }
}