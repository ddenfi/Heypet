package com.c22ps104.heypetanimalwelfare.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c22ps104.heypetanimalwelfare.api.CategoriesItem
import com.c22ps104.heypetanimalwelfare.databinding.ItemMainPostBinding

class ListFeedsAdapter: RecyclerView.Adapter<ListFeedsAdapter.ListViewHolder>() {
    private val listFeed: ArrayList<CategoriesItem> = ArrayList()
//    private lateinit var onItemClickCallback: OnItemClickCallback

//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<CategoriesItem>) {
        listFeed.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

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

//            itemView.setOnClickListener {
//                onItemClickCallback.onItemClicked(listFeed[absoluteAdapterPosition])
//                // TODO intent
//            }
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

//    interface OnItemClickCallback {
//        fun onItemClicked(data: CategoriesItem)
//
//    }
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CategoriesItem>() {
            override fun areItemsTheSame(oldItem: CategoriesItem, newItem: CategoriesItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CategoriesItem, newItem: CategoriesItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}