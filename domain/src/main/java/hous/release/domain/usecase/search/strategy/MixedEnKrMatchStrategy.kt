package hous.release.domain.usecase.search.strategy

class MixedEnKrMatchStrategy : RuleNameMatchStrategy {

    override fun isMatched(search: String, target: String): Boolean {
        var t = 0
        val diff = target.length - search.length
        val searchLen = search.length
        if (diff < 0) return false // 검색어가 더 길면 false를 리턴한다.
        for (i in 0..diff) {
            t = 0
            while (t < searchLen) {
                // 만약, index : t에 해당하는 search 문자가 한글 이고, index : t + i에 해당하는 input 문자가 한글이면
                if (search[t].isKrConsonant() && target[i + t].isKorean()) {
                    val targetConsonant = target[t].mapToKrConsonant()
                    val searchConsonant = search[i + t]
                    // 초성이 다르면 false를 리턴한다.
                    if (searchConsonant != targetConsonant) break
                    t++
                } else {
                    if (search[t] != target[i + t]) break
                    t++
                }
            }

            if (t == searchLen) return true
        }
        return false
    }

    private fun Char.isKrConsonant(): Boolean = this in KR_CONSONANT_LIST
    private fun Char.mapToKrConsonant(): Char {
        val consonantIndex = (this.code - KR_START_UNICODE) / KR_CONSONANT_UNIT
        return KR_CONSONANT_LIST[consonantIndex]
    }

    private fun Char.isKorean(): Boolean = this.code in KR_START_UNICODE..KR_END_UNICODE

    companion object {
        private const val KR_START_UNICODE = 0xAC00 // 가 code
        private const val KR_END_UNICODE = 0xD7A3 // 힣 code
        private const val KR_CONSONANT_UNIT = 588 // 각자음 마다 가지는 글자수
        private val KR_CONSONANT_LIST: List<Char> = listOf(
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ',
            'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ',
            'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ',
            'ㅎ'
        )
    }
}
