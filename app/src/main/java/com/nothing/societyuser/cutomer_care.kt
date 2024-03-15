package com.nothing.societyuser

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nothing.societyuser.databinding.ActivityCutomerCareBinding

class cutomer_care : AppCompatActivity() {

    private lateinit var binding : ActivityCutomerCareBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCutomerCareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIssueSendEmail.setOnClickListener(){
            var content = binding.edtIssueDesc.text.toString()
            var title = binding.edtIssueTitle.text.toString()
            var email = "akshitdonda117@gmail.com"

            if (title.isEmpty()  && email.isEmpty() && content.isEmpty()){
                Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show()
            }else{
                sendEmail(email,content,title)
            }

        }

        binding.btnIssueCall.setOnClickListener(){
            makeCall()
        }
    }

    private fun sendEmail(email: String,content: String, title:String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.putExtra(Intent.EXTRA_SUBJECT, title)
        intent.putExtra(Intent.EXTRA_TEXT, content)
        intent.setType("message/rfc822")
        startActivity(Intent.createChooser(intent, "Send issue..."))
    }

    private fun makeCall(){
        val phoneNumber = "0000000000"

        // Create intent with action ACTION_CALL
        val intent = Intent(Intent.ACTION_CALL)

        // Set the data for the intent (phone number to call)
        intent.data = Uri.parse("tel:$phoneNumber")

        // Check if the CALL_PHONE permission is granted before making the call
        if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            // Start the call
            startActivity(intent)
        } else {
            // Request CALL_PHONE permission if not granted
            requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE), 1)
        }
    }
}