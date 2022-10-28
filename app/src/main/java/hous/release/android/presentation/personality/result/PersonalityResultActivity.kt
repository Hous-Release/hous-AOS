package hous.release.android.presentation.personality.result

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityPersonalityResultBinding
import hous.release.android.util.binding.BindingActivity
import hous.release.domain.entity.HomyType
import timber.log.Timber

@AndroidEntryPoint
class PersonalityResultActivity :
    BindingActivity<ActivityPersonalityResultBinding>(R.layout.activity_personality_result) {
    private val personalityResultViewModel: PersonalityResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = personalityResultViewModel
        initColor()
        initTitlePosition()
        initBackOnClickListener()
    }

    private fun initTitlePosition() {
        personalityResultViewModel.fromTestResult.observe(this) {
            when (intent.getStringExtra("location")) {
                "result" -> personalityResultViewModel.initFromTestResult(true)
                else -> personalityResultViewModel.initFromTestResult(false)
            }
        }
    }

    private fun initBackOnClickListener() {
        binding.tvPersonalityResultComplete.setOnClickListener {
            finish()
        }

        binding.ivPersonalityResultBack.setOnClickListener {
            finish()
        }
    }

    private fun initColor() {
        personalityResultViewModel.resultData.observe(this) { result ->
            when (HomyType.valueOf(result.color)) {
                HomyType.RED -> {
                    with(binding) {
                        tvPersonalityResultName.setTextColor(
                            ContextCompat.getColor(
                                this@PersonalityResultActivity,
                                R.color.hous_red
                            )
                        )
                        tvPersonalityResultTitle.setTextColor(
                            ContextCompat.getColor(
                                this@PersonalityResultActivity,
                                R.color.hous_red
                            )
                        )
                        tvPersonalityResultRecommendTitle.setTextColor(
                            ContextCompat.getColor(
                                this@PersonalityResultActivity,
                                R.color.hous_red
                            )
                        )
                        ivPersonalityResultCheck1.setImageResource(R.drawable.ic_personality_result_check_red)
                        ivPersonalityResultCheck2.setImageResource(R.drawable.ic_personality_result_check_red)
                    }
                }
                HomyType.YELLOW -> {
                    with(binding) {
                        tvPersonalityResultName.setTextColor(
                            ContextCompat.getColor(
                                this@PersonalityResultActivity,
                                R.color.hous_yellow
                            )
                        )
                        tvPersonalityResultTitle.setTextColor(
                            ContextCompat.getColor(
                                this@PersonalityResultActivity,
                                R.color.hous_yellow
                            )
                        )
                        tvPersonalityResultRecommendTitle.setTextColor(
                            ContextCompat.getColor(
                                this@PersonalityResultActivity,
                                R.color.hous_yellow
                            )
                        )
                        ivPersonalityResultCheck1.setImageResource(R.drawable.ic_personality_result_check_yellow)
                        ivPersonalityResultCheck2.setImageResource(R.drawable.ic_personality_result_check_yellow)
                    }
                }
                HomyType.GREEN -> {
                    with(binding) {
                        tvPersonalityResultName.setTextColor(
                            ContextCompat.getColor(
                                this@PersonalityResultActivity,
                                R.color.hous_green
                            )
                        )
                        tvPersonalityResultTitle.setTextColor(
                            ContextCompat.getColor(
                                this@PersonalityResultActivity,
                                R.color.hous_green
                            )
                        )
                        tvPersonalityResultRecommendTitle.setTextColor(
                            ContextCompat.getColor(
                                this@PersonalityResultActivity,
                                R.color.hous_green
                            )
                        )
                        ivPersonalityResultCheck1.setImageResource(R.drawable.ic_personality_result_check_green)
                        ivPersonalityResultCheck2.setImageResource(R.drawable.ic_personality_result_check_green)
                    }
                }
                HomyType.BLUE -> {
                    with(binding) {
                        tvPersonalityResultName.setTextColor(
                            ContextCompat.getColor(
                                this@PersonalityResultActivity,
                                R.color.hous_blue
                            )
                        )
                        tvPersonalityResultTitle.setTextColor(
                            ContextCompat.getColor(
                                this@PersonalityResultActivity,
                                R.color.hous_blue
                            )
                        )
                        tvPersonalityResultRecommendTitle.setTextColor(
                            ContextCompat.getColor(
                                this@PersonalityResultActivity,
                                R.color.hous_blue
                            )
                        )
                        ivPersonalityResultCheck1.setImageResource(R.drawable.ic_personality_result_check_blue)
                        ivPersonalityResultCheck2.setImageResource(R.drawable.ic_personality_result_check_blue)
                    }
                }
                HomyType.PURPLE -> {
                    with(binding) {
                        tvPersonalityResultName.setTextColor(
                            ContextCompat.getColor(
                                this@PersonalityResultActivity,
                                R.color.hous_purple
                            )
                        )
                        tvPersonalityResultTitle.setTextColor(
                            ContextCompat.getColor(
                                this@PersonalityResultActivity,
                                R.color.hous_purple
                            )
                        )
                        tvPersonalityResultRecommendTitle.setTextColor(
                            ContextCompat.getColor(
                                this@PersonalityResultActivity,
                                R.color.hous_purple
                            )
                        )
                        ivPersonalityResultCheck1.setImageResource(R.drawable.ic_personality_result_check_purple)
                        ivPersonalityResultCheck2.setImageResource(R.drawable.ic_personality_result_check_purple)
                    }
                }
                else -> Timber.e(getString(R.string.not_supported_type))
            }
        }
    }
}
