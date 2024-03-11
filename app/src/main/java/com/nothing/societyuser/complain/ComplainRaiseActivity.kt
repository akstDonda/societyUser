package com.nothing.societyuser.complain

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nothing.societyuser.databinding.ActivityComplainRaiseBinding

class ComplainRaiseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComplainRaiseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplainRaiseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //spinner icon on open spinner
        binding.complainSelectImageBtn.setOnClickListener {
            binding.complainSelectSpinnerBtn.show()
        }

        binding.complainHistoryBtn.setOnClickListener {
            intentFun(ComplainRaiseHistory::class.java)
        }

    }
    //intent
    fun intentFun(destination : Class<*>){
        var intent = Intent(this, destination)
        startActivity(intent)
    }
    //toast
    fun toastFun(message : String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }




}