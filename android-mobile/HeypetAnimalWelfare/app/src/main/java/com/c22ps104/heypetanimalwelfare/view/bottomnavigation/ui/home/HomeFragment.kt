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
import com.c22ps104.heypetanimalwelfare.api.CategoriesItem
import com.c22ps104.heypetanimalwelfare.databinding.FragmentHomeBinding
import com.c22ps104.heypetanimalwelfare.view.bottomnavigation.ui.scan.ScanViewModel
import com.c22ps104.heypetanimalwelfare.view.upload.UploadActivity
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
//    private lateinit var listFeed: List<CategoriesItem>

    private var db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.getAllFeeds()

        fireStoreListener()
        setHasOptionsMenu(true)
        setRecyclerView()

        return root
    }

    private fun setRecyclerView() {
        binding.rvPosts.setHasFixedSize(true)
        binding.rvPosts.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbFeeds.visibility = View.VISIBLE
            } else {
                binding.pbFeeds.visibility = View.GONE
            }
        }

        homeViewModel.feedsResult.observe(requireActivity()) {
            binding.rvPosts.adapter = ListFeedsAdapter(it)
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
            Toast.makeText(requireContext(), "Filter Clicked", Toast.LENGTH_SHORT).show()
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
                when (dc.type) {
                    DocumentChange.Type.ADDED -> Log.d(
                        "Firestore_ADDED",
                        "date: ${dc.document.data.getValue("date")}"
                    )
                    DocumentChange.Type.MODIFIED -> Log.d(
                        "Firestore_MODIFIED",
                        "date: ${dc.document.data.getValue("date")}"
                    )
                    DocumentChange.Type.REMOVED -> Log.d(
                        "Firestore_REMOVED",
                        "date: ${dc.document.data.getValue("date")}"
                    )

                }
                readFirestoreData()
            }
        }
    }

    private fun readFirestoreData() {
        db.collection("feeds")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        Log.d("Firestore", document.id + " => " + document.data)
                    }
                } else {
                    Log.w("Firestore", "Error getting documents")
                }
            }
    }

}