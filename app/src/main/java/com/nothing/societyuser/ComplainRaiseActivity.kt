package com.nothing.societyuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupWindow
import com.nothing.societyuser.databinding.ActivityComplainRaiseBinding
import com.nothing.societyuser.databinding.ActivityWalletBinding
import com.skydoves.powerspinner.PowerSpinnerView

class ComplainRaiseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComplainRaiseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplainRaiseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.complainSelectImageBtn.setOnClickListener {
            binding.complainSelectSpinnerBtn.show()
        }

    }



}