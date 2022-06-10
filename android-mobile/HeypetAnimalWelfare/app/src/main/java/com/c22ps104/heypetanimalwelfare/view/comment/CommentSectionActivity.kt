package com.c22ps104.heypetanimalwelfare.view.comment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.c22ps104.heypetanimalwelfare.adapter.ListCommentAdapter
import com.c22ps104.heypetanimalwelfare.api.CommentModel
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_USER_NAME
import com.c22ps104.heypetanimalwelfare.databinding.ActivityCommnetSectionBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CommentSectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommnetSectionBinding
    private val adapter by lazy {
        ListCommentAdapter()
    }

    private var db = FirebaseFirestore.getInstance()
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommnetSectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Comment"

        preferencesHelper = PreferencesHelper(this)
        val id = intent.getStringExtra("EXTRA_ID")

        if (id != null) {
            Log.d("Call Firebase",id)
            fireStoreListener(id)
            binding.btnSend.setOnClickListener {
                insertFirestoreData(id)
            }
        } else {
            Log.d("Call Firebase","null")
        }

        setRecyclerView()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun setRecyclerView() {
        binding.rvDetailPostComments.setHasFixedSize(true)
        binding.rvDetailPostComments.layoutManager = LinearLayoutManager(this)
        binding.rvDetailPostComments.adapter = adapter
    }

    private fun fireStoreListener(idFeed: String) {
        val docRef = db.collection("comments")

        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("Firestore", "listen:error", e)
                return@addSnapshotListener
            }

            for (dc in snapshot!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> Log.d(
                        "Firestore_ADDED",
                        "added:"
                    )

                    DocumentChange.Type.MODIFIED -> Log.d(
                        "Firestore_MODIFIED",
                        "date:"
                    )

                    DocumentChange.Type.REMOVED -> Log.d(
                        "Firestore_REMOVED",
                        "date:"
                    )
                }

                readFirestoreData(idFeed)
            }
        }
    }

    private fun readFirestoreData(idFeed: String) {
        Log.d("Firestore","Id Feed $idFeed")

        db.collection("comments")
            .whereEqualTo("idFeed",idFeed)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val listComment: ArrayList<CommentModel> = ArrayList()
                    for (document in task.result) {
                        val comment = CommentModel()
                        comment.name = document.data.getValue("name").toString()
                        comment.comment = document.data.getValue("comment").toString()
                        comment.date = document.data.getValue("date").toString()
                        comment.idFeed = document.data.getValue("idFeed").toString()

                        Log.d("Comment","${comment.name} ${comment.date} ${comment.comment} ${comment.idFeed}")
                        listComment.add(comment)
                    }
                    adapter.setData(listComment)
                } else {
                    Log.w("Firestore", "Error getting documents.", task.exception)
                }
            }.addOnFailureListener{
                Log.d("Firestore","Read Firestore error ${it.message}")
            }
    }

    private fun insertFirestoreData(idFeed: String){
        val userName = preferencesHelper.getString(PREF_USER_NAME)
        // Get Date
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd-HH-mm",Locale.getDefault())
        val date = dateFormat.format(calendar.time).toString()

        // Get Comment
        val comment = binding.etCommentComment.text.toString()

        val data = hashMapOf(
            "name" to userName,
            "idFeed" to idFeed,
            "comment" to comment,
            "date" to date
        )

        db.collection("comments").add(data).addOnSuccessListener{
            Log.d("Comment","Success")
        }.addOnFailureListener{
            Log.d("Comment",it.toString())
        }
    }
}