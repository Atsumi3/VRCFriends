package jp.bizen.vrcfriends.android.presentation.startup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import jp.bizen.vrcfriends.android.R
import jp.bizen.vrcfriends.android.databinding.ActivityStartupBinding
import jp.bizen.vrcfriends.android.extensions.dataBinding
import jp.bizen.vrcfriends.android.presentation.home.HomeActivity
import jp.bizen.vrcfriends.android.presentation.login.LoginRootActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartupActivity : AppCompatActivity(R.layout.activity_startup) {
    private val binding: ActivityStartupBinding by dataBinding()
    private val viewModel: StartupViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        viewModel.presentInputTwoFactorTokenScreen.observe(this, Observer {
            val inputView = EditText(this)
            inputView.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
            AlertDialog.Builder(this)
                .setTitle("二段階認証が必要です")
                .setView(inputView)
                .setPositiveButton(
                    "OK"
                ) { _, _ -> viewModel.onInputTwoFactorTokenDialogOk(inputView.text.toString()) }
                .setNegativeButton("ログイン画面に戻る") { _, _ -> viewModel.onInputTwoFactorTokenDialogBackToLogin()}
                .setCancelable(false)
                .create().show()
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