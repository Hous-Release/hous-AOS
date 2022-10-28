package hous.release.data.entity

import hous.release.domain.entity.TestScore

data class TestScoreEntity(
    val light: Int,
    val noise: Int,
    val clean: Int,
    val smell: Int,
    val introversion: Int
) {
    fun toTestScore() = TestScore(
        light = this.light,
        noise = this.noise,
        clean = this.clean,
        smell = this.smell,
        introversion = this.introversion
    )
}
