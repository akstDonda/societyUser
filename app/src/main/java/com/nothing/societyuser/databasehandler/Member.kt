package com.nothing.societyuser.databasehandler

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class Member() {
    private var transactions = ArrayList<Transaction>()
    init {
        val auth = Firebase.auth
        val user = auth.currentUser

        val fireStore = Firebase.firestore

        fireStore.collection("members").document(user!!.uid).get()
            .addOnSuccessListener { document ->
                this.transactions = document.toObject(Member::class.java)?.transactions ?: ArrayList()
            }
    }
}