package com.c22ps104.heypetanimalwelfare.view.bottomnavigation.ui.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.adapter.ListReminderAdapter
import com.c22ps104.heypetanimalwelfare.data.ReminderEntity
import com.c22ps104.heypetanimalwelfare.databinding.FragmentReminderBinding
import com.c22ps104.heypetanimalwelfare.utils.AlarmReceiver
import com.c22ps104.heypetanimalwelfare.utils.AlarmReceiver.Companion.EXTRA_ID
import com.c22ps104.heypetanimalwelfare.utils.AlarmReceiver.Companion.EXTRA_TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReminderFragment : Fragment() {

    private val rotateOpen:Animation by lazy{ AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_open_anim)}
    private val rotateClose:Animation by lazy{ AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_close_anim)}
    private val fromBottom:Animation by lazy{ AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom_anim)}
    private val toBottom:Animation by lazy{ AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom_anim)}

    private lateinit var reminderBroadcast:AlarmReceiver

    private var clicked = false

    private var _binding: FragmentReminderBinding? = null

    private val adapter by lazy {
        ListReminderAdapter()
    }

    private val viewModel:ReminderViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentReminderBinding.inflate(inflater, container, false)

        reminderBroadcast = AlarmReceiver()

        binding.fabReminderAdd.setOnClickListener {
            onAddButtonClicked()
        }
        binding.fabReminderAddOne.setOnClickListener {
            startActivity(Intent(activity, ReminderAddOneTimeActivity::class.java))
        }
        binding.fabReminderAddRepeating.setOnClickListener {
            startActivity(Intent(activity, ReminderAddRepeatingActivity::class.java))
        }

        viewModel.getAllReminder().observe(requireActivity()){
            adapter.setData(it)
        }

        activity?.registerReceiver(broadcastReceiver, IntentFilter("REMINDER_BROADCAST"))

        setRecyclerView()
        return binding.root
    }

    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getIntExtra(EXTRA_ID,0)
            val type = intent.getIntExtra(EXTRA_TYPE,0)

            if (id != 0 && type != 2){
                Log.d("Reminder","Delete Success")
                lifecycleScope.launch(Dispatchers.IO){
                    viewModel.deleteReminder(id)
                }
            }
        }
    }

    private fun setRecyclerView() {
        binding.rvReminder.setHasFixedSize(true)
        binding.rvReminder.layoutManager = LinearLayoutManager(requireContext())
        binding.rvReminder.adapter = adapter

        adapter.setOnItemClickCallback(object : ListReminderAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ReminderEntity) {
                lifecycleScope.launch{viewModel.deleteReminder(data.id)}
                context?.let { reminderBroadcast.cancelAlarm(it,data.id) }
                Toast.makeText(context,"Reminder ${data.reminderName} deleted",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onPause() {
        lifecycleScope.launch {
            delay(100L)
            if (clicked) { onAddButtonClicked()}
        }
        super.onPause()
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked:Boolean) {
        if (!clicked) {
            binding.fabReminderAdd.startAnimation(rotateOpen)
            binding.fabReminderAddOne.startAnimation(fromBottom)
            binding.fabReminderAddRepeating.startAnimation(fromBottom)
        } else  {
            binding.fabReminderAdd.startAnimation(rotateClose)
            binding.fabReminderAddOne.startAnimation(toBottom)
            binding.fabReminderAddRepeating.startAnimation(toBottom)
        }
    }

    private fun setVisibility(clicked:Boolean) {
        if(!clicked){
            binding.fabReminderAddOne.visibility = View.VISIBLE
            binding.fabReminderAddRepeating.visibility = View.VISIBLE
        } else {
            binding.fabReminderAddOne.visibility = View.GONE
            binding.fabReminderAddRepeating.visibility = View.GONE
        }
    }


    override fun onDestroy() {
        activity?.unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}