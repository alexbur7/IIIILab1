package entity

enum class Area(val value: Int, val russianName: String) {
    NE(0, "Северо-Восточный административный округ"),
    NORTH(1, "Северный административный округ"),
    NW(2, "Северо-западный административный округ"),
    EAST(3, "Восточный административный округ"),
    CENTER(4, "Центральный административный округ"),
    SE(5, "Юго-Восточный административный округ"),
    SOUTH(6, "Южный административный округ"),
    SW(7, "Юго-Западный административный округ"),
    MOSCOW_REGION(8, "Подмосковье");

    companion object {
        fun from(value: String?): Area {
            return values().find { it.russianName == value } ?: NE
        }
    }
}