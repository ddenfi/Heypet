package com.c22ps104.heypetanimalwelfare.view.bottomnavigation.ui.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper
import com.c22ps104.heypetanimalwelfare.databinding.FragmentProfileBinding
import com.c22ps104.heypetanimalwelfare.view.login.LoginActivity
import com.c22ps104.heypetanimalwelfare.view.welcome.WelcomeActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this)[ProfileViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textView
//       profileViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        // TODO memindahkan logout ke activity settings
        preferencesHelper = PreferencesHelper(requireActivity())
        setupView()

        return root
    }

    private fun setupView() {
        binding.btnLogout.setOnClickListener {
            preferencesHelper.clear()

            val intentToLogin = Intent(requireActivity(), WelcomeActivity::class.java)
            intentToLogin.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intentToLogin)
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}