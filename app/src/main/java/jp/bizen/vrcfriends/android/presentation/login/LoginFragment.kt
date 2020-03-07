package jp.bizen.vrcfriends.android.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import jp.bizen.vrcfriends.android.R
import jp.bizen.vrcfriends.android.databinding.FragmentLoginBinding
import jp.bizen.vrcfriends.android.extensions.dataBinding
import jp.bizen.vrcfriends.android.presentation.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding: FragmentLoginBinding by dataBinding()
    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setupVMEventSubscriber()
    }

    private fun setupVMEventSubscriber() {
        viewModel.navigateToHomeScreen.observe(viewLifecycleOwner, Observer {
            HomeActivity.createIntent(requireActivity())
        })

        viewModel.presentErrorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_SHORT
            ).show()
        })

        viewModel.presentWelcomeMessage.observe(viewLifecycleOwner, Observer {
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