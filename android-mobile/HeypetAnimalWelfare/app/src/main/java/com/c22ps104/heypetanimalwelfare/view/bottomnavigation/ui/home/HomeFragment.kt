package com.c22ps104.heypetanimalwelfare.view.bottomnavigation.ui.home

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c22ps104.heypetanimalwelfare.R
import com.c22ps104.heypetanimalwelfare.databinding.FragmentHomeBinding
import com.c22ps104.heypetanimalwelfare.view.upload.UploadActivity
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fireStoreListener()

//        setRecyclerView()

        setHasOptionsMenu(true)
        return root
    }

    private fun setRecyclerView() {
        binding.rvPosts.setHasFixedSize(true)
        binding.rvPosts.layoutManager = LinearLayoutManager(requireContext())
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