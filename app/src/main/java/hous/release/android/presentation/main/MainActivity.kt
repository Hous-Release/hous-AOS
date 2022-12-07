package hous.release.android.presentation.main

import android.os.Bundle
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityMainBinding
import hous.release.android.util.ToastMessageUtil
import hous.release.android.util.binding.BindingActivity
import timber.log.Timber
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private var onBackPressedTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBottomNavigationView()
        initBackPressedCallback()
    }

    private fun initBottomNavigationView() {
        val navController = findNavController()
        binding.botNavMain.setupWithNavController(navController)
    }

    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fc_main) as NavHostFragment
        return navHostFragment.navController
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback {
            val fragmentId: Int? = findNavController(R.id.fc_main).currentDestination?.id
            fragmentId?.also { id ->
                if (id == R.id.housFragment) {
                    val curTime = System.currentTimeMillis()
                    val gap = curTime - onBackPressedTime
                    if (gap > WAITING_DEADLINE) {
                        onBackPressedTime = curTime
                        ToastMessageUtil.showToast(
                            this@MainActivity,
                            getString(R.string.finish_app_toast_msg)
                        )
                        return@addCallback
                    }
                    finishAffinity()
                    System.runFinalization()
                    exitProcess(0)
                }
            } ?: Timber.e(getString(R.string.null_point_exception))
        }
    }

    fun moveToToDoFragment() {
        binding.botNavMain.selectedItemId = R.id.toDoFragment
    }

    companion object {
        private const val WAITING_DEADLINE = 2000L
    }
}
