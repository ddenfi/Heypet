package com.c22ps104.heypetanimalwelfare.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c22ps104.heypetanimalwelfare.api.PostsItem
import com.c22ps104.heypetanimalwelfare.databinding.ItemProfilePostBinding

class ListUserPostAdapter: RecyclerView.Adapter<ListUserPostAdapter.ListViewHolder>() {
    private val listFeed: ArrayList<PostsItem> = ArrayList()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<PostsItem>) {
        listFeed.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private var binding: ItemProfilePostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: PostsItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(feed.photo)
                    .into(imgPostPicture)
            }
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(listFeed[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemProfilePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFeed[position])
    }

    override fun getItemCount(): Int = listFeed.size

    interface OnItemClickCallback {
        fun onItemClicked(data: PostsItem)
    }
}