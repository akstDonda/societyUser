package com.nothing.societyuser

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nothing.societyuser.databinding.ActivityAddMoneyAnimationBinding
import com.nothing.societyuser.databinding.ActivityAnimationTransactionSuccesBinding
import com.nothing.societyuser.databinding.ActivityTransactionHistoryBinding
import com.nothing.societyuser.fragment.BottomActivity

class animation_transaction_succes : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationTransactionSuccesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationTransactionSuccesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var extra = intent.getStringExtra("key");

        if(extra == "unsuccess") {
            binding.complainAnimation.visibility = View.GONE
            binding.complainAnimationSecond.visibility = View.VISIBLE
        }


        binding.transactionAmount.setOnClickListener {
            var intent = Intent(this, BottomActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}