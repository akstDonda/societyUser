package com.nothing.societyuser.wallet

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nothing.societyuser.R
import java.util.Calendar

class DabitCardActivity : AppCompatActivity() {
    // ...

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dabit_card)

        // TODO: Add increase money in user wallet
        val addAmount = intent.getStringExtra("amount")
        val intAddAmount = addAmount?.toIntOrNull() ?: 0

        val btn:Button = findViewById(R.id.add_money_wallet_debitCard)
        btn.setOnClickListener(){

            updateCurrentAmount(intAddAmount)
        }

    }

    private fun updateCurrentAmount(addAmount: Int) {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val uid = user.uid
            val memberDocRef = firestore.collection("member").document(uid)

            // Get the current value of "currentAmount" from Firestore
            memberDocRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val currentAmount = documentSnapshot.getLong("currentAmount")?.toInt() ?: 0
                        val newAmount = currentAmount + addAmount

                        // Update the "currentAmount" field with the new amount
                        memberDocRef.update("currentAmount", newAmount)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Amount Added", Toast.LENGTH_SHORT).show()
                                finish() // Close the activity if the update is successful
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error Adding Amount", Toast.LENGTH_SHORT).show()
                                e.printStackTrace()
                                finish() // Close the activity if the update fails
                            }
                    } else {
                        Toast.makeText(this, "Document does not exist", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error retrieving current amount", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
        }
    }


}
