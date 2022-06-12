package com.c22ps104.heypetanimalwelfare.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c22ps104.heypetanimalwelfare.data.ReminderEntity
import com.c22ps104.heypetanimalwelfare.databinding.ItemReminderBinding
import java.text.SimpleDateFormat
import java.util.*

class ListReminderAdapter : RecyclerView.Adapter<ListReminderAdapter.ListViewHolder>() {

    private val listReminder: ArrayList<ReminderEntity> = ArrayList()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ReminderEntity>) {
        listReminder.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private var binding: ItemReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ReminderEntity) {
            val timeFormat = SimpleDateFormat("HH\nmm", Locale.getDefault())
            val dateFormat = SimpleDateFormat("dd-MM-yy", Locale.getDefault())

            with(binding) {
                tvItemReminderName.text = data.reminderName
                tvItemReminderTime.text = timeFormat.format(data.reminderDate)
                tvItemReminderType.text =
                    if (data.reminderType == 1) dateFormat.format(data.reminderDate) else "Everyday"
            }

            binding.ivItemReminderDelete.setOnClickListener {
                onItemClickCallback.onItemClicked(listReminder[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemReminderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listReminder[position])
    }

    override fun getItemCount() = listReminder.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ReminderEntity)
    }
}
