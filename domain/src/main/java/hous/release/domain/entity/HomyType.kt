package hous.release.domain.entity

enum class HomyType {
    YELLOW, RED, BLUE, PURPLE, GREEN, GRAY;

    fun getSaveImageFileName(number: Int): String = "${this.name.lowercase()}_$number"
}
