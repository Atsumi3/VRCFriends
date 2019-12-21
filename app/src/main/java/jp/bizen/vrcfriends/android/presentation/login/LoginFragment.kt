package jp.bizen.vrcfriends.android.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import jp.bizen.vrcfriends.android.R
import jp.bizen.vrcfriends.android.databinding.FragmentLoginBinding
import jp.bizen.vrcfriends.android.presentation.home.HomeActivity

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupVMEventSubscriber()
        return binding.root
    }

    private fun setupVMEventSubscriber() {
        viewModel.navigateToHomeScreen.observe(this, Observer {
            HomeActivity.createIntent(requireActivity())
        })

        viewModel.presentErrorMessage.observe(this, Observer {
            Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_SHORT
            ).show()
        })

        viewModel.presentWelcomeMessage.observe(this, Observer {
            Toast.makeText(
                requireContext(),
                getString(R.string.welcome_message, it),
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}