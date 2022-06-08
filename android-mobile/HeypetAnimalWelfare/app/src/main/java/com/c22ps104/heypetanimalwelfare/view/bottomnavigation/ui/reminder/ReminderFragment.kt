package com.c22ps104.heypetanimalwelfare.view.bottomnavigation.ui.reminder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.adapter.ListReminderAdapter
import com.c22ps104.heypetanimalwelfare.data.ReminderEntity
import com.c22ps104.heypetanimalwelfare.databinding.FragmentReminderBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReminderFragment : Fragment() {

    private val rotateOpen:Animation by lazy{ AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_open_anim)}
    private val rotateClose:Animation by lazy{ AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_close_anim)}
    private val fromBottom:Animation by lazy{ AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom_anim)}
    private val toBottom:Animation by lazy{ AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom_anim)}

    private var clicked = false

    private var _binding: FragmentReminderBinding? = null

    private val adapter by lazy {
        ListReminderAdapter()
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this)[ReminderViewModel::class.java]

        _binding = FragmentReminderBinding.inflate(inflater, container, false)


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

        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        binding.rvReminder.setHasFixedSize(true)
        binding.rvReminder.layoutManager = LinearLayoutManager(requireContext())
        binding.rvReminder.adapter = adapter

        adapter.setOnItemClickCallback(object : ListReminderAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ReminderEntity) {
                val toDetailActivity = Intent(requireActivity(),DetailReminderActivity::class.java)
                toDetailActivity.putExtra("EXTRA_REMINDER", data)
                startActivity(toDetailActivity)
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}