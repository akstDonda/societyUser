package com.nothing.societyuser.setting

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.nothing.societyuser.R
import com.nothing.societyuser.SettingFragment
import com.nothing.societyuser.databinding.ActivityUpdateUserProfileBinding
import java.util.UUID

class updateUserProfile : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateUserProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUserUid: String
    private lateinit var firestore: FirebaseFirestore
    private  var  updateImgUrlDownload =""

    //for image pick
    private val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val resultCode = result.resultCode
        val data = result.data

        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            binding.updateUserProfileScroll.visibility = View.GONE
            binding.updateUserProfileRoot.setBackgroundColor(Color.LTGRAY)
            binding.updateUserProfileRoot.gravity = Gravity.CENTER
            binding.spinKit.visibility = View.VISIBLE
            val fileUri = data?.data

            if (fileUri != null) {
                val imageRef = Firebase.storage.reference.child("profileImage/${UUID.randomUUID()}")

                imageRef.putFile(fileUri)
                    .addOnSuccessListener { uploadTask ->
                        uploadTask.storage.downloadUrl.addOnSuccessListener { uri ->
                            val imageUrl = uri.toString()

                            // Update the userImage field for the user document
                            val uid = FirebaseAuth.getInstance().currentUser?.uid
                            val db = FirebaseFirestore.getInstance()
                            uid?.let { uid ->
                                val userRef = db.collection("member").document(uid)
                                userRef.update("userImage", imageUrl)
                                    .addOnSuccessListener {
                                        println("User image updated successfully")
                                    }
                                    .addOnFailureListener { e ->
                                        // If the document does not exist or there's an error updating, create the field and set the value
                                        userRef.set(mapOf("userImage" to imageUrl))
                                            .addOnSuccessListener {
                                                println("User image field created successfully")
                                            }
                                            .addOnFailureListener { e ->
                                                println("Error creating user image field: $e")
                                            }
                                    }
                            }

                            Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                            binding.updateProfileImageBtn.setImageURI(fileUri)
                            binding.updateUserProfileScroll.visibility = View.VISIBLE
                            binding.updateUserProfileRoot.gravity = Gravity.TOP
                            binding.spinKit.visibility = View.GONE
                            binding.updateUserProfileRoot.setBackgroundColor(Color.WHITE)
                        }
                    }
                    .addOnFailureListener { e ->
                        println("Error uploading image: $e")
                        Toast.makeText(this, "Error uploading image", Toast.LENGTH_SHORT).show()
                    }
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //update button
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

        //image take
        binding.updateProfileImageBtn.setOnClickListener(){
            ImagePicker.with(this)
                .crop()
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }

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