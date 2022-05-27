package com.c22ps104.heypetanimalwelfare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.c22ps104.heypetanimalwelfare.data.ListPostData
import com.c22ps104.heypetanimalwelfare.databinding.ItemMainPostBinding

class ListProfilePostPagingAdapter :
    PagingDataAdapter<ListPostData, ListProfilePostPagingAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    // test

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(private var binding: ItemMainPostBinding) :
        RecyclerView.ViewHolder(binding.root)
    {
        fun bind(post: ListPostData) {
            // TODO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemMainPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)

        if(item != null) {
            holder.bind(item)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(item)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListPostData?)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListPostData>() {
            override fun areItemsTheSame(oldItem: ListPostData, newItem: ListPostData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListPostData, newItem: ListPostData): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}