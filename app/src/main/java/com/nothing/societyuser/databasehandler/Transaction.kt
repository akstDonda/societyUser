package com.nothing.societyuser.databasehandler

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Date

class Transaction(userId: String,  amountOfTran: Number) {
    var id: String = Date().time.toString()
    private var amount: Number = amountOfTran
    private var date: Date = Date()
    private var completed: Boolean = false

    init {
        val fireStore = Firebase.firestore
        fireStore.collection("transactions").document(userId).update(id, this)
    }

}