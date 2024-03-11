package com.nothing.societyuser.welcome

import HomeCategoryAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.nothing.societyuser.Model.HomeCategoryModel
import com.nothing.societyuser.R
import com.nothing.societyuser.databinding.ActivityMoreCategotyBinding

class MoreCategotyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoreCategotyBinding
    private lateinit var homeCategoryAdapter: HomeCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoreCategotyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Assuming you have a RecyclerView in your layout with the id "recyclerView"


        // Create dummy data for testing (replace this with your actual data)
        val dummyDataList = ArrayList<HomeCategoryModel>()
        dummyDataList.add(HomeCategoryModel(R.drawable.ic_launcher_foreground, "Category"))
        dummyDataList.add(HomeCategoryModel(R.drawable.ic_launcher_foreground, "Category"))
        dummyDataList.add(HomeCategoryModel(R.drawable.ic_launcher_foreground, "Category"))
        dummyDataList.add(HomeCategoryModel(R.drawable.ic_launcher_foreground, "Category"))
        dummyDataList.add(HomeCategoryModel(R.drawable.ic_launcher_foreground, "Category"))
        dummyDataList.add(HomeCategoryModel(R.drawable.ic_launcher_foreground, "Category"))
        dummyDataList.add(HomeCategoryModel(R.drawable.ic_launcher_foreground, "Category"))
        dummyDataList.add(HomeCategoryModel(R.drawable.ic_launcher_foreground, "Category"))
        dummyDataList.add(HomeCategoryModel(R.drawable.ic_launcher_foreground, "Category"))
        // Add more categories as needed

        // Initialize the adapter with your data
        homeCategoryAdapter = HomeCategoryAdapter(dummyDataList)
        binding.connectAdminRv.layoutManager = GridLayoutManager(this, 4)
        binding.connectAdminRv.adapter = homeCategoryAdapter

        homeCategoryAdapter = HomeCategoryAdapter(dummyDataList)
        binding.connectUserRv.layoutManager = GridLayoutManager(this, 4)
        binding.connectUserRv.adapter = homeCategoryAdapter

        homeCategoryAdapter = HomeCategoryAdapter(dummyDataList)
        binding.connectHelpRv.layoutManager = GridLayoutManager(this, 4)
        binding.connectHelpRv.adapter = homeCategoryAdapter
    }
}
