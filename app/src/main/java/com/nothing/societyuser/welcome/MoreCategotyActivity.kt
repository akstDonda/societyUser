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
        dummyDataList.add(HomeCategoryModel(R.drawable.pay_icon, "Pay"))
        dummyDataList.add(HomeCategoryModel(R.drawable.baseline_add_complain_24, "complain"))
        dummyDataList.add(HomeCategoryModel(R.drawable.baseline_meeting_24, "Meeting"))
        dummyDataList.add(HomeCategoryModel(R.drawable.baseline_chat_24, "Chat"))
        dummyDataList.add(HomeCategoryModel(R.drawable.wallet_icon, "wallet"))
        dummyDataList.add(HomeCategoryModel(R.drawable.history_icon, "Complain History"))
        dummyDataList.add(HomeCategoryModel(R.drawable.baseline_customer_care_24, "Customer Care"))
        dummyDataList.add(HomeCategoryModel(R.drawable.outline_profile_24, "Account"))
        dummyDataList.add(HomeCategoryModel(R.drawable.outline_info_24, "Information"))
        dummyDataList.add(HomeCategoryModel(R.drawable.baseline_edit_profile_24, "Edit Profile"))
        // Add more categories as needed


        homeCategoryAdapter = HomeCategoryAdapter(dummyDataList)
        binding.connectUserRv.layoutManager = GridLayoutManager(this, 4)
        binding.connectUserRv.adapter = homeCategoryAdapter


    }
}
