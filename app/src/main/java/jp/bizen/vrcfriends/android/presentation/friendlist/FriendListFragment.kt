package jp.bizen.vrcfriends.android.presentation.friendlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.bizen.vrcfriends.android.R
import jp.bizen.vrcfriends.android.databinding.FragmentFriendListBinding

class FriendListFragment : Fragment() {
    private lateinit var binding: FragmentFriendListBinding
    private val viewModel: FriendListViewModel by lazy {
        ViewModelProviders.of(this)[FriendListViewModel::class.java]
    }
    private val adapter: FriendListViewAdapter by lazy {
        FriendListViewAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_friend_list,
            container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupList(binding.list)
        setupVMEventSubscriber()
        return binding.root
    }

    private fun setupList(list: RecyclerView) {
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(context)
    }

    private fun setupVMEventSubscriber() {
        viewModel.data.observe(this, Observer {
            adapter.data = it
        })

        viewModel.navigateToStartupScreen.observe(this, Observer {
//            StartupActivity.createIntent(requireActivity())
        })

        viewModel.presentErrorMessage.observe(this, Observer {
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