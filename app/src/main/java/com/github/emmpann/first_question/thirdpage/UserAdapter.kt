package com.github.emmpann.first_question.thirdpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.emmpann.first_question.data.DataItem
import com.github.emmpann.first_question.databinding.UserItemBinding

class UserAdapter : PagingDataAdapter<DataItem, UserAdapter.UserItemView>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class UserItemView(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItem) {
            binding.apply {
                tvUsername.text = "${data.firstName} ${data.lastName}"
                tvEmail.text = data.email
                Glide.with(binding.root.context)
                    .load(data.avatar)
                    .circleCrop()
                    .into(ivUser)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemView {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserItemView(binding)
    }

    override fun onBindViewHolder(holder: UserItemView, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(data) }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem,
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}