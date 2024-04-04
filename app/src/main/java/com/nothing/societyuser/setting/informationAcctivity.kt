package com.nothing.societyuser.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nothing.societyuser.R

class informationAcctivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_acctivity)
        supportActionBar?.title = "❕ Information"
    }
}