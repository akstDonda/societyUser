import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nothing.societyuser.Model.TransactionHistoryModel
import com.nothing.societyuser.R
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionHistoryAdapter(private var transactionList: List<TransactionHistoryModel>) :
    RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.transaction_date)
        val amountTextView: TextView = itemView.findViewById(R.id.transaction_amount)
        val statusTextView: TextView = itemView.findViewById(R.id.transaction_sattus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item_transaction_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactionList[position]

        val dateFormat = SimpleDateFormat("MM/dd/yy", Locale.getDefault())
        val formattedDate = dateFormat.format(transaction.date)

        holder.dateTextView.text = "Date: $formattedDate"
        holder.amountTextView.text = "Amount: ${transaction.amount}"
        holder.statusTextView.text = "Status: ${transaction.status}"
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    // Update the data in the adapter
    fun updateData(newTransactionList: List<TransactionHistoryModel>) {
        transactionList = newTransactionList
        notifyDataSetChanged()
    }
}
