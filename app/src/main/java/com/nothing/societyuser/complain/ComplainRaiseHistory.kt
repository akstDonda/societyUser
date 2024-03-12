package com.nothing.societyuser.complain

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.nothing.societyuser.Adapter.ComplainHistoryAdapter
import com.nothing.societyuser.CONSTANTS
import com.nothing.societyuser.Model.complainHistoryModel
import com.nothing.societyuser.R
import com.nothing.societyuser.databinding.ActivityComplainRaiseHistoryBinding
import java.util.*

class ComplainRaiseHistory : AppCompatActivity() {
    private lateinit var binding: ActivityComplainRaiseHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplainRaiseHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Dummy data for testing
        var dummyData: List<complainHistoryModel> = listOf();

        // Initialize RecyclerView and set up the adapter

        binding.complainRaiseHistoryRv.layoutManager = LinearLayoutManager(this)
        val adapter = ComplainHistoryAdapter(dummyData)
        binding.complainRaiseHistoryRv.adapter = adapter


        val db = Firebase.firestore
        val user = Firebase.auth.currentUser

        db.collection("member").document(user!!.uid).get()
            .addOnSuccessListener {
                val societyId = it.data!!["societyId"]

                Log.d("societyId", societyId.toString())
                if (societyId != null) {
                    db.collection("societies").document(societyId.toString()).collection("complains")
                        .get()
                        .addOnSuccessListener {
                            result ->
                            for (document in result) {
                                val data = document.data

                                // TODO: get Image using URL, ref https://firebase.google.com/docs/storage/android/download-files

                                val url = data["imageUrl"]

                                var status = "Pending"
                                if (data["resolved"] == "true")
                                    status = "Resolved!"
                                else if (data["approved"] == "true") {
                                    status = "Approved!"
                                }
                                else if (data["rejected"] == "true") {
                                    status = "Rejected!"
                                }

                                val timestamp = data["timestamp"] as Timestamp
                                dummyData = dummyData + listOf(
                                    complainHistoryModel(
                                        R.drawable.ic_launcher_foreground,
                                        data["type"] as String,
                                        data["title"] as String,
                                        timestamp.toDate(),
                                        status,
                                        data["description"] as String,
                                    )
                                )
                            }

                            adapter.updateData(dummyData)
                        }
                }
            }

    }
}
