package com.nothing.societyuser.Adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nothing.societyuser.Model.complainHistoryModel
import com.nothing.societyuser.R
import com.nothing.societyuser.complain.ComplainRaiseHistory
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ComplainHistoryAdapter(context: Context, var complainList: List<complainHistoryModel>) :
    RecyclerView.Adapter<ComplainHistoryAdapter.ComplainHistoryViewHolder>() {

        var context: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplainHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item_complain_history, parent, false)

        return ComplainHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComplainHistoryViewHolder, position: Int) {
        val complain = complainList[position]

        // Load image from URL using Picasso or Glide
        // Replace "complain.img" with the actual URL from complainHistoryModel


        Glide.with(context)
            .load(complain.imgUrl)
            .placeholder(R.drawable.logo_black_primary) // Optional placeholder image while loading
            .error(R.drawable.logo_black_primary) // Optional error image if loading fails
            .centerCrop()
            .into(holder.img)
        // OR

//         Glide.with(holder.itemView.context).load(encodedString).into(holder.img)

        holder.type.text = "Type: ${complain.type}"
        holder.title.text = "Title: ${complain.title}"

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        holder.date.text = "Date: ${dateFormat.format(complain.date)}"

        holder.status.text = "Status: ${complain.status}"
        holder.description.text = "Description: ${complain.description}"
    }

    override fun getItemCount(): Int {
        return complainList.size
    }

    inner class ComplainHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.complain_history_img)
        val type: TextView = itemView.findViewById(R.id.complain_history_type)
        val title: TextView = itemView.findViewById(R.id.complain_history_title)
        val date: TextView = itemView.findViewById(R.id.complain_history_date)
        val status: TextView = itemView.findViewById(R.id.complain_history_status)
        val description: TextView = itemView.findViewById(R.id.complain_history_desc)
    }

    fun updateData(complainList: List<complainHistoryModel>) {
        this.complainList = complainList
        notifyDataSetChanged()
    }
}

