package com.nothing.societyuser.complain

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nothing.societyuser.Model.complainModel
import com.nothing.societyuser.R
import com.nothing.societyuser.databinding.ActivityComplainRaiseBinding
import java.util.UUID

class ComplainRaiseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComplainRaiseBinding
    var selectedComplaintType: String? = null
    var complainModel = complainModel()
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                binding.complainImageLl.visibility = View.GONE
                binding.complainInfoLl.visibility = View.GONE
                binding.submitComplainBtn.visibility = View.GONE
                binding.complainRootLl.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.light_white
                    )
                )
                binding.spinKit.visibility = View.VISIBLE
                val fileUri = data?.data!!

                Firebase.storage.reference.child("complainImage/${UUID.randomUUID()}")
                    .putFile(fileUri).addOnCompleteListener {
                        if (it.isSuccessful) {

                            it.result.storage.downloadUrl.addOnCompleteListener {
                                complainModel.imageUrl = it.toString()
                                binding.complainImageLl.visibility = View.VISIBLE
                                binding.complainInfoLl.visibility = View.VISIBLE
                                binding.submitComplainBtn.visibility = View.VISIBLE
                                binding.spinKit.visibility = View.GONE
                                binding.complainRootLl.setBackgroundColor(
                                    ContextCompat.getColor(
                                        this,
                                        R.color.white
                                    )
                                )
                                binding.complainImage.setImageURI(fileUri)

                            }


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
        binding = ActivityComplainRaiseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //for select image in internal
        binding.complainImage.setOnClickListener {
            ImagePicker.with(this)
//                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .crop()
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        //spinner icon on open spinner
        binding.complainSelectImageBtn.setOnClickListener {
            binding.complainSelectSpinnerBtn.show()
        }

        binding.complainHistoryBtn.setOnClickListener {
            intentFun(ComplainRaiseHistory::class.java)
        }

        //submit complain
        binding.submitComplainBtn.setOnClickListener {
            binding.complainSelectSpinnerBtn.setOnSpinnerItemSelectedListener<String> { _, _, _, item ->
                selectedComplaintType = item
            }
            if (selectedComplaintType == "--select no of--" || selectedComplaintType == null) {
                // Show a validation message or perform any action you want
                Toast.makeText(this, "Please select Complain Type.🌀", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.editTextComplainTitle.text.toString().isEmpty()) {
                binding.editTextComplainTitle.error = "Title is required ❔"
            } else if (binding.editTextIssueDescription.text.toString().isEmpty()) {
                binding.editTextIssueDescription.error = "Description is required ❔"
            } else if (complainModel.imageUrl.isNullOrEmpty()) {
                Toast.makeText(this, "Please select Complain Image.", Toast.LENGTH_SHORT).show()
            } else {
                toastFun("Submit")
                complainModel.type = selectedComplaintType ?: "Default value or handle null case"
                complainModel.title = binding.editTextComplainTitle.text.toString()
                complainModel.description = binding.editTextIssueDescription.text.toString()

                complainModel.upload(this)
            }

        }

    }

    //intent
    fun intentFun(destination: Class<*>) {
        var intent = Intent(this, destination)
        startActivity(intent)
    }

    //toast
    fun toastFun(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


}