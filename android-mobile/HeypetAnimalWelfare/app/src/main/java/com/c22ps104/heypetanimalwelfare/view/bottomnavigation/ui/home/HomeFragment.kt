package com.c22ps104.heypetanimalwelfare.view.bottomnavigation.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.adapter.ListFeedsAdapter
import com.c22ps104.heypetanimalwelfare.api.PostCategoriesItem
import com.c22ps104.heypetanimalwelfare.databinding.FragmentHomeBinding
import com.c22ps104.heypetanimalwelfare.view.comment.CommentSectionActivity
import com.c22ps104.heypetanimalwelfare.view.bottomnavigation.ModalBottomSheet
import com.c22ps104.heypetanimalwelfare.view.upload.UploadActivity
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    val bottomSheet = ModalBottomSheet()
    private val adapter by lazy {
        ListFeedsAdapter()
    }
//    private lateinit var listFeed: List<CategoriesItem>

    private var db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        homeViewModel.filterState.observe(requireActivity()) {
            sendRequest(it)
            Log.d("filter state",it)
        }
        homeViewModel.feedsResult.observe(requireActivity()) {
            adapter.setData(it)
            binding.btnRefresh.visibility = View.GONE
        }
        fireStoreListener()
        setHasOptionsMenu(true)
        setRecyclerView()
        setupView()

        return root
    }

    private fun setupView() {
        binding.btnRefresh.setOnClickListener {
            homeViewModel.getAllFeeds()
            binding.rvPosts.scrollToPosition(0)
        }
    }

    private fun setRecyclerView() {
        binding.rvPosts.setHasFixedSize(true)
        binding.rvPosts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPosts.adapter = adapter

        adapter.setOnItemClickCallback(object : ListFeedsAdapter.OnItemClickCallback{
            override fun onItemClicked(data: PostCategoriesItem) {
                val toCommentSection = Intent(requireActivity(), CommentSectionActivity::class.java)
                toCommentSection.putExtra("EXTRA_ID", data.idFeeds)
                startActivity(toCommentSection)
            }
        })

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbFeeds.visibility = View.VISIBLE
            } else {
                binding.pbFeeds.visibility = View.GONE
            }
        }


//        listFeedsAdapter.setOnItemClickCallback(object: ListFeedsAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: CategoriesItem) {
//                // TODO (intent to detail)
//            }
//        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_filter) {
            bottomSheet.show(childFragmentManager, ModalBottomSheet.TAG)
        }
        if (id == R.id.menu_add_post) {
            startActivity(Intent(activity, UploadActivity::class.java))
            Toast.makeText(requireContext(), "Add Post Clicked", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fireStoreListener() {
        val docRef = db.collection("feeds")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("Firestore", "listen:error", e)
                return@addSnapshotListener
            }
            for (dc in snapshot!!.documentChanges) {
//                when (dc.type) {
//                    DocumentChange.Type.ADDED -> Log.d(
//                        "Firestore_ADDED",
//                        "date: ${dc.document.data.getValue("date")}"
//                    )
//                    DocumentChange.Type.MODIFIED -> Log.d(
//                        "Firestore_MODIFIED",
//                        "date: ${dc.document.data.getValue("date")}"
//                    )
//                    DocumentChange.Type.REMOVED -> Log.d(
//                        "Firestore_REMOVED",
//                        "date: ${dc.document.data.getValue("date")}"
//                    )
//                }
                binding.btnRefresh.visibility = View.VISIBLE
//                readFirestoreData()
            }
        }
    }

    private fun sendRequest(test: String) {
        when (test) {
            "0" -> homeViewModel.getAllFeeds()
            "1" -> homeViewModel.getCategorizedFeed("1")
            "2" -> homeViewModel.getCategorizedFeed("2")
            "3" -> homeViewModel.getCategorizedFeed("3")
            "4" -> homeViewModel.getCategorizedFeed("4")
            else -> homeViewModel.getAllFeeds()
        }
    }
}