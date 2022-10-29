package hous.release.android.presentation.profile

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityProfileEditBinding
import hous.release.android.util.binding.BindingActivity

@AndroidEntryPoint
class ProfileEditActivity : BindingActivity<ActivityProfileEditBinding>(R.layout.activity_profile_edit) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}