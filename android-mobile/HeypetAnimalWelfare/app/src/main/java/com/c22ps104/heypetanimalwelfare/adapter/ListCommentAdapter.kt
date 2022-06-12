package com.c22ps104.heypetanimalwelfare.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c22ps104.heypetanimalwelfare.api.CommentModel
import com.c22ps104.heypetanimalwelfare.databinding.ItemCommentBinding

class ListCommentAdapter : RecyclerView.Adapter<ListCommentAdapter.ListViewHolder>() {

    private val listComment: ArrayList<CommentModel> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<CommentModel>) {
        listComment.apply {
            clear()
            addAll(data)
        }

        notifyDataSetChanged()
    }

    inner class ListViewHolder(private var binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CommentModel) {
            binding.tvItemCommnetName.text = data.name
            binding.tvItemCommentComment.text = data.comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listComment[position])
    }

    override fun getItemCount() = listComment.size
}