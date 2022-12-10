package hous.release.domain.entity

data class TestScore(
    val light: Int = 0,
    val noise: Int = 0,
    val clean: Int = 0,
    val smell: Int = 0,
    val introversion: Int = 0
) {
    fun toFloatList(): List<Float> = listOf(
        (light * 22 + 36).toFloat(),
        (clean * 22 + 36).toFloat(),
        (introversion * 22 + 36).toFloat(),
        (smell * 22 + 36).toFloat(),
        (noise * 22 + 36).toFloat(),
        (light * 22 + 36).toFloat()
    )
}
