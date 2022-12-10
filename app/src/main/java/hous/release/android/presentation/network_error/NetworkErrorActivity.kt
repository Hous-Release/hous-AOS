package hous.release.android.presentation.network_error

import android.content.Intent
import android.os.Bundle
import hous.release.android.R
import hous.release.android.databinding.ActivityNetworkErrorBinding
import hous.release.android.presentation.main.MainActivity
import hous.release.android.util.binding.BindingActivity

class NetworkErrorActivity :
    BindingActivity<ActivityNetworkErrorBinding>(R.layout.activity_network_error) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnNetworkErrorRetry.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            })
        }
    }
}
