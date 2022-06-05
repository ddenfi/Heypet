package com.c22ps104.heypetanimalwelfare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c22ps104.heypetanimalwelfare.api.CategoriesItem
import com.c22ps104.heypetanimalwelfare.databinding.ItemMainPostBinding

class ListFeedsAdapter(private val listFeed: List<CategoriesItem>) : RecyclerView.Adapter<ListFeedsAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }

    inner class ListViewHolder(private var binding: ItemMainPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: CategoriesItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(feed.photo)
                    .into(imgPostPicture)
                tvPostDescription.text = feed.description
                tvPostTag.text = when(feed.categoryId) {
                    1 -> "Story"
                    2 -> "Breed"
                    3 -> "Adoption"
                    4 -> "Tips"
                    else -> ""
                }
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(listFeed[absoluteAdapterPosition])
                // TODO intent
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemMainPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFeed[position])
    }

    override fun getItemCount(): Int = listFeed.size

    interface OnItemClickCallback {
        fun onItemClicked(data: CategoriesItem)
    }
}