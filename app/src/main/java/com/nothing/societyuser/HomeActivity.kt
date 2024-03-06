package com.nothing.societyuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nothing.societyuser.Adapter.HomeCategoryAdapter
import com.nothing.societyuser.Adapter.HomeCategoryModel
import com.nothing.societyuser.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // Sample data list (replace this with your actual data)
        val dataList = ArrayList<HomeCategoryModel>()
        dataList.add(HomeCategoryModel(R.drawable.plain_dollar, "Pay"))
        dataList.add(HomeCategoryModel(R.drawable.plain_dollar, "ad"))
        dataList.add(HomeCategoryModel(R.drawable.plain_dollar, "ddfe"))
        dataList.add(HomeCategoryModel(R.drawable.plain_dollar, "Pay"))
        dataList.add(HomeCategoryModel(R.drawable.plain_dollar, "Pay"))
        dataList.add(HomeCategoryModel(R.drawable.plain_dollar, "Pay"))

        var adapter = HomeCategoryAdapter(dataList)
        val rv: RecyclerView = findViewById(R.id.category_rv)
        binding.categoryRv.adapter = adapter
        binding.categoryRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}