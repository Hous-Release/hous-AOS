package hous.release.domain.entity.response

data class PersonalityResult(
    val badPersonalityImageUrl: String = "",
    val badPersonalityName: String = "",
    val color: String = "",
    val description: List<String> = emptyList(),
    val goodPersonalityImageUrl: String = "",
    val goodPersonalityName: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val recommendTitle: String = "",
    val recommendTodo: List<String> = emptyList(),
    val title: String = ""
)
