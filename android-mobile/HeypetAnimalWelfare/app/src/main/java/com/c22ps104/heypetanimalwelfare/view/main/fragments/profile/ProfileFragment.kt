package com.c22ps104.heypetanimalwelfare.view.main.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.adapter.ListFeedsAdapter
import com.c22ps104.heypetanimalwelfare.adapter.ListUserPostAdapter
import com.c22ps104.heypetanimalwelfare.api.PostsItem
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_ID
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_TOKEN
import com.c22ps104.heypetanimalwelfare.databinding.FragmentProfileBinding
import com.c22ps104.heypetanimalwelfare.view.comment.CommentSectionActivity
import com.c22ps104.heypetanimalwelfare.view.welcome.WelcomeActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val adapter by lazy {
        ListUserPostAdapter()
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var preferencesHelper: PreferencesHelper
    private val viewModel:ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        preferencesHelper = PreferencesHelper(requireActivity())
        val token = preferencesHelper.getString(PREF_TOKEN)
        if (token != null){
            viewModel.getUserDetail(token)
        }

        setUpView()
        setUpRecyclerView()
        setHasOptionsMenu(true)

        return root
    }

    private fun setUpView() {
        binding.imgProfilePicture.setImageResource(R.drawable.default_photo_profile)
        viewModel.userDetails.observe(viewLifecycleOwner){
            Glide.with(requireContext())
                .load(it.photo)
                .placeholder(R.drawable.default_photo_profile)
                .into(binding.imgProfilePicture)
            binding.tvProfileName.text = it.name
            binding.tvProfileBio.text = it.bio
            binding.tvPetName.text = it.bio
            viewModel.getUserPost(it.id.toString())
        }
        viewModel.feedsResult.observe(requireActivity()){
            adapter.setData(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvProfilePosts.setHasFixedSize(true)
        binding.rvProfilePosts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProfilePosts.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserPostAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PostsItem) {
                //TODO post detail
//                val toDetailPost = Intent(requireActivity(), CommentSectionActivity::class.java)
//                toDetailPost.putExtra("EXTRA_ID", data.idFeeds)
//                startActivity(toDetailPost)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_logout -> {
                preferencesHelper.clear()

                val intentToWelcome = Intent(requireActivity(), WelcomeActivity::class.java)
                intentToWelcome.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intentToWelcome)
                activity?.finish()
            }
            R.id.btn_edit -> {
                startActivity(Intent(requireActivity(), EditProfileActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}