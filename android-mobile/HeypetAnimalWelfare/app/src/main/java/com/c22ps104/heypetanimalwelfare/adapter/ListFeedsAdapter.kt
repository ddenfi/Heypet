package com.c22ps104.heypetanimalwelfare.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.api.PostsItem
import com.c22ps104.heypetanimalwelfare.databinding.ItemHomePostBinding

class ListFeedsAdapter : RecyclerView.Adapter<ListFeedsAdapter.ListViewHolder>() {

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

    inner class ListViewHolder(private var binding: ItemHomePostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: PostsItem) {

            with(binding) {
                Glide.with(itemView.context)
                    .load(feed.photo)
                    .into(imgPostPicture)

                tvPostDescription.text = feed.description

                tvPostTag.text = when (feed.categoryId) {
                    "1" -> "Story"
                    "2" -> "Breed"
                    "3" -> "Adoption"
                    "4" -> "Tips"
                    else -> ""
                }

                tvProfileName.text = feed.userName
                Glide.with(itemView.context)
                    .load(feed.userPhoto)
                    .placeholder(R.drawable.default_photo_profile)
                    .into(imgProfilePicture)
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(listFeed[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemHomePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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