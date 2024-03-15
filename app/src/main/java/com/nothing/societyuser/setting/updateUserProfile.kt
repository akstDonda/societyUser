package com.nothing.societyuser.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nothing.societyuser.R
import com.nothing.societyuser.SettingFragment
import com.nothing.societyuser.databinding.ActivityUpdateUserProfileBinding

class updateUserProfile : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateUserProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUserUid: String
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.updateFirebaseProfileBtn.setOnClickListener {

            val userName = binding.updateProfileUserNameEdt.text.toString()
            val houseNo = binding.updateProfileHouseNoEdt.text.toString()

            if (userName.isEmpty() || houseNo.isEmpty()) {
                Toast.makeText(this@updateUserProfile, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proceed with profile update
            updateProfile()

            // Start UserProfileActivity
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun updateProfile() {
        auth = FirebaseAuth.getInstance()
        currentUserUid = auth.currentUser?.uid ?: ""
        firestore = FirebaseFirestore.getInstance()
        val userName = binding.updateProfileUserNameEdt.text.toString().trim()
        val houseNo = binding.updateProfileHouseNoEdt.text.toString().trim()

        // Get a reference to the user document in Firestore
        val userDocRef = firestore.collection("member").document(currentUserUid)

        // Update user data in Firestore
        userDocRef.update("userName", userName, "userHouseNo", houseNo)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to update profile: $it", Toast.LENGTH_SHORT).show()
            }
    }
}