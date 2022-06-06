package com.c22ps104.heypetanimalwelfare.view.bottomnavigation.ui.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.databinding.FragmentReminderBinding

class ReminderFragment : Fragment() {

    private val rotateOpen:Animation by lazy{ AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_open_anim)}
    private val rotateClose:Animation by lazy{ AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_close_anim)}
    private val fromBottom:Animation by lazy{ AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom_anim)}
    private val toBottom:Animation by lazy{ AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom_anim)}

    private var clicked = false

    private var _binding: FragmentReminderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[ReminderViewModel::class.java]

        _binding = FragmentReminderBinding.inflate(inflater, container, false)

//        val textView: TextView = binding.textNotifications
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.fabReminderAdd.setOnClickListener {
            onAddButtonClicked()
        }

        binding.fabReminderAddOne.setOnClickListener {
            Toast.makeText(requireContext(),"One Time",Toast.LENGTH_SHORT).show()
        }
        binding.fabReminderAddRepeating.setOnClickListener {
            Toast.makeText(requireContext(),"Repeating",Toast.LENGTH_SHORT).show()
        }
        return binding.root
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