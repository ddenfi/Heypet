package com.c22ps104.heypetanimalwelfare.view.comment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.c22ps104.heypetanimalwelfare.adapter.ListCommentAdapter
import com.c22ps104.heypetanimalwelfare.api.CommentModel
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper
import com.c22ps104.heypetanimalwelfare.data.PreferencesHelper.Companion.PREF_USER_NAME
import com.c22ps104.heypetanimalwelfare.databinding.ActivityCommentBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class CommentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommentBinding
    private val adapter by lazy {
        ListCommentAdapter()
    }

    private var db = FirebaseFirestore.getInstance()
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        preferencesHelper = PreferencesHelper(this)
        val id = intent.getStringExtra("EXTRA_ID")

        if (id != null) {
            Log.d("Call Firebase", id)
            fireStoreListener(id)

            binding.btnSend.setOnClickListener {
                insertFirestoreData(id)
            }

        } else {
            Log.d("Call Firebase", "null")
        }

        setRecyclerView()
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Comment"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun setRecyclerView() {
        binding.rvComments.setHasFixedSize(true)
        binding.rvComments.layoutManager = LinearLayoutManager(this)
        binding.rvComments.adapter = adapter
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
        Log.d("Firestore", "Id Feed $idFeed")

        db.collection("comments")
            .whereEqualTo("idFeed", idFeed)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val listComment: ArrayList<CommentModel> = ArrayList()

                    for ((i, document) in task.result.withIndex()) {
                        listComment.add(
                            CommentModel(
                                name = document.data.getValue("name") as String,
                                comment = document.data.getValue("comment") as String,
                                date = document.data.getValue("date") as String,
                                idFeed = document.data.getValue("idFeed") as String
                            )
                        )

                        Log.d(
                            "Comment",
                            "${listComment[i].name} ${listComment[i].date} ${listComment[i].comment} ${listComment[i].idFeed}"
                        )
                    }

                    adapter.setData(listComment)
                } else {
                    Log.w("Firestore", "Error getting documents.", task.exception)
                }
            }.addOnFailureListener {
                Log.d("Firestore", "Read Firestore error ${it.message}")
            }
    }

    private fun insertFirestoreData(idFeed: String) {
        val userName = preferencesHelper.getString(PREF_USER_NAME)
        // Get Date
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd-HH-mm", Locale.getDefault())
        val date = dateFormat.format(calendar.time).toString()

        // Get Comment
        val comment = binding.etCommentInput.text.toString()

        val data = hashMapOf(
            "name" to userName,
            "idFeed" to idFeed,
            "comment" to comment,
            "date" to date
        )

        db.collection("comments").add(data).addOnSuccessListener {
            Log.d("Comment", "Success")
        }.addOnFailureListener {
            Log.d("Comment", it.toString())
        }
    }
}