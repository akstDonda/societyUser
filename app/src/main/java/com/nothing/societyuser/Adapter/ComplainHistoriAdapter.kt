package com.nothing.societyuser.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nothing.societyuser.Model.complainHistoryModel
import com.nothing.societyuser.R
import java.text.SimpleDateFormat
import java.util.*

class ComplainHistoryAdapter(private val complainList: List<complainHistoryModel>) :
    RecyclerView.Adapter<ComplainHistoryAdapter.ComplainHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplainHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item_complain_history, parent, false)

        return ComplainHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComplainHistoryViewHolder, position: Int) {
        val complain = complainList[position]

        holder.img.setImageResource(complain.img)
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
}
