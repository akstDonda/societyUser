package com.nothing.societyuser.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nothing.societyuser.databinding.SingleItemCategoryBinding


class HomeCategoryAdapter(var dataList: ArrayList<HomeCategoryModel>) : RecyclerView.Adapter<HomeCategoryAdapter.CategoryViewHolder>() {


    inner class CategoryViewHolder(var binding: SingleItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleItemCategoryBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            val currentItem = dataList[position]

            // Set the data for each item in the RecyclerView
            holder.binding.img.setImageResource(currentItem.image)
            holder.binding.txt.text = currentItem.name

        }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
