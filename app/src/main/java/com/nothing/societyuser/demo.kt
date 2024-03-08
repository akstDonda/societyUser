package com.nothing.societyuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class demo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        var btn= findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            Toast.makeText(this,"hellp",Toast.LENGTH_LONG).show()

        }
    }
}