package com.c22ps104.heypetanimalwelfare.view.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.databinding.ActivityBottomNavigationBinding
import com.c22ps104.heypetanimalwelfare.databinding.BottomSheetLayoutBinding
import com.c22ps104.heypetanimalwelfare.view.main.fragments.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbNavigation)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.bottom_nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home,
            R.id.navigation_scan,
            R.id.navigation_reminder,
            R.id.navigation_profile
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}

class ModalBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetLayoutBinding.inflate(inflater, container, false)

        homeViewModel =
            ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        setupBottomSheet()

        return binding.root
    }

    private fun setupBottomSheet() {
        binding.filterGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            when (checkedIds[0]) {
                binding.chipAll.id -> {
                    homeViewModel.setFilterState("0")
                    Log.d("filter state update", "")
                }

                binding.chipStory.id -> homeViewModel.setFilterState("1")
                binding.chipBreeding.id -> homeViewModel.setFilterState("2")
                binding.chipAdoption.id -> homeViewModel.setFilterState("3")
                binding.chipTips.id -> homeViewModel.setFilterState("4")

                else -> homeViewModel.setFilterState("0")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}