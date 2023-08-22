package hous.release.android.presentation.personality.result

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityPersonalityResultBinding
import hous.release.android.util.ToastMessageUtil.showToast
import hous.release.android.util.UiEvent
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.component.PersonalityResultImage
import hous.release.android.util.dialog.LoadingDialogFragment
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.setOnSingleClickListener
import hous.release.designsystem.theme.HousTheme

@AndroidEntryPoint
class PersonalityResultActivity :
    BindingActivity<ActivityPersonalityResultBinding>(R.layout.activity_personality_result) {
    private val personalityResultViewModel: PersonalityResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = personalityResultViewModel
        initPersonalityResult()
        initBackBtnClickListener()
        initPersonalityImage()
        initDownloadBtnClickListener()
        collectUiEvent()
    }

    private fun initPersonalityResult() {
        personalityResultViewModel.getPersonalityResult(
            intent.getStringExtra(RESULT_COLOR) ?: "BLUE"
        )
    }

    private fun initPersonalityImage() {
        repeatOnStarted {
            personalityResultViewModel.uiState.collect { uiState ->
                binding.cvPersonalityResultImage.setContent {
                    HousTheme {
                        PersonalityResultImage(
                            homyType = uiState.color,
                            imageUrl = uiState.imageUrl
                        )
                    }
                }
            }
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnPersonalityResultBack.setOnClickListener {
            finish()
        }
    }

    private fun initDownloadBtnClickListener() {
        binding.btnPersonalityResultDownload.setOnSingleClickListener {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSION_REQUEST_CODE
                    )
                } else {
                    personalityResultViewModel.downloadImage()
                }
            } else {
                personalityResultViewModel.downloadImage()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    personalityResultViewModel.downloadImage()
                } else {
                    showToast(this, "권한을 설정해주세요.")
                }
                return
            }
        }
    }

    private fun collectUiEvent() {
        repeatOnStarted {
            personalityResultViewModel.uiEvent.collect { uiEvent ->
                val loadingDialogFragment =
                    supportFragmentManager.findFragmentByTag(LoadingDialogFragment.TAG) as? LoadingDialogFragment

                when (uiEvent) {
                    UiEvent.LOADING -> {
                        loadingDialogFragment?.show(
                            supportFragmentManager, LoadingDialogFragment.TAG
                        )
                    }
                    UiEvent.SUCCESS -> {
                        loadingDialogFragment?.dismiss()
                        showToast(this, getString(R.string.personality_result_download_success))
                    }
                    UiEvent.ERROR -> {
                        loadingDialogFragment?.dismiss()
                        showToast(this, getString(R.string.personality_result_download_failure))
                    }
                }
            }
        }
    }

    companion object {
        const val RESULT_COLOR = "resultColor"
        private const val PERMISSION_REQUEST_CODE = 1
    }
}
