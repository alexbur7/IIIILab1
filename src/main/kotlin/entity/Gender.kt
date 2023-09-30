package entity

enum class Gender(val value: Int, val sign: String) {
    MAN(1, "М"),
    WOMAN(0, "Ж");

    companion object {
        fun from(value: String?): Gender {
            return values().find { it.sign == value } ?: MAN
        }
    }
}