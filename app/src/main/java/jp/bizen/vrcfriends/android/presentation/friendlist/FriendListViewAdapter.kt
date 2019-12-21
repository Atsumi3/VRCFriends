package jp.bizen.vrcfriends.android.presentation.friendlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import jp.bizen.vrcfriends.android.R
import jp.bizen.vrcfriends.android.databinding.ItemFriendRowBinding
import jp.bizen.vrcfriends.android.model.entity.Friend

class FriendListViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data: List<Friend> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemFriendRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_friend_row, parent, false
        )
        return ContentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContentViewHolder -> {
                holder.bind(data[position])
            }
        }
    }

    companion object {
        class ContentViewHolder(private val binding: ItemFriendRowBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(data: Friend) {
                if (binding.viewModel == null) {
                    binding.viewModel =
                        ItemFriendListContentViewModel(
                            data
                        )
                } else {
                    binding.viewModel!!.data = data
                }
            }
        }
    }
}