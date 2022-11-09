package hous.release.android.presentation.profile.homie

import android.os.Bundle
import androidx.activity.viewModels
import hous.release.android.R
import hous.release.android.databinding.ActivityHomieProfileBinding
import hous.release.android.util.binding.BindingActivity

class HomieProfileActivity :
    BindingActivity<ActivityHomieProfileBinding>(R.layout.activity_homie_profile) {
    private val homieProfileViewModel: HomieProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = homieProfileViewModel
    }
}
