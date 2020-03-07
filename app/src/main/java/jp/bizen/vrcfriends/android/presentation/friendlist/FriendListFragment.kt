package jp.bizen.vrcfriends.android.presentation.friendlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.bizen.vrcfriends.android.R
import jp.bizen.vrcfriends.android.databinding.FragmentFriendListBinding
import jp.bizen.vrcfriends.android.extensions.dataBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FriendListFragment : Fragment(R.layout.fragment_friend_list) {
    private val binding: FragmentFriendListBinding by dataBinding()
    private val viewModel: FriendListViewModel by viewModel()
    private val adapter: FriendListViewAdapter by lazy {
        FriendListViewAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setupList(binding.list)
        setupVMEventSubscriber()
    }

    private fun setupList(list: RecyclerView) {
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(context)
    }

    private fun setupVMEventSubscriber() {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter.data = it
        })

        viewModel.navigateToStartupScreen.observe(viewLifecycleOwner, Observer {
//            StartupActivity.createIntent(requireActivity())
        })

        viewModel.presentErrorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    companion object {
        fun newInstance() = FriendListFragment()
    }
}