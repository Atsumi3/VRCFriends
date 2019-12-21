package jp.bizen.vrcfriends.android.presentation.startup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import jp.bizen.vrcfriends.android.R
import jp.bizen.vrcfriends.android.databinding.ActivityStartupBinding
import jp.bizen.vrcfriends.android.presentation.home.HomeActivity
import jp.bizen.vrcfriends.android.presentation.login.LoginRootActivity

class StartupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartupBinding
    private val viewModel: StartupViewModel by lazy {
        ViewModelProviders.of(this)[StartupViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_startup)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupVMEventSubscriber()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    private fun setupVMEventSubscriber() {
        viewModel.navigateToLoginScreen.observe(this, Observer {
            LoginRootActivity.createIntent(this)
        })

        viewModel.navigateToHomeScreen.observe(this, Observer {
            HomeActivity.createIntent(this)
        })

        viewModel.presentWelcomeMessage.observe(this, Observer {
            Toast.makeText(this, getString(R.string.welcome_message, it), Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        fun createIntent(context: Context) {
            val intent = Intent(context, StartupActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}