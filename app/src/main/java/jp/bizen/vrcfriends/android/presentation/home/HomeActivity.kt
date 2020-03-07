package jp.bizen.vrcfriends.android.presentation.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import jp.bizen.vrcfriends.android.R
import jp.bizen.vrcfriends.android.databinding.ActivityHomeBinding
import jp.bizen.vrcfriends.android.extensions.dataBinding
import jp.bizen.vrcfriends.android.presentation.dialog.YesNoDialogFragment
import jp.bizen.vrcfriends.android.presentation.friendlist.FriendListFragment
import jp.bizen.vrcfriends.android.presentation.startup.StartupActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(R.layout.activity_home) {
    private val binding: ActivityHomeBinding by dataBinding()
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        setupVMEventSubscriber()
        supportFragmentManager.commit {
            replace(R.id.fragment_container, FriendListFragment.newInstance())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                viewModel.onMenuLogoutClick()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_LOGOUT_CONFIRMATION -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        Toast.makeText(this, "まだよ", Toast.LENGTH_SHORT).show()
                        viewModel.onLogoutConfirmationOk()
                    }
                }
            }
        }
    }

    private fun setupVMEventSubscriber() {
        viewModel.navigateToStartupScreen.observe(this, Observer {
            StartupActivity.createIntent(this)
        })

        viewModel.presentLogoutConfirmationDialog.observe(this, Observer {
            YesNoDialogFragment.show(
                supportFragmentManager,
                "確認", "ログアウトしますか?", REQUEST_CODE_LOGOUT_CONFIRMATION
            )
        })
    }

    companion object {
        private const val REQUEST_CODE_LOGOUT_CONFIRMATION = 1000
        fun createIntent(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}