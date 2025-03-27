package com.example.weekdays
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ajfit.R

class weeksListAdapter(
    private val context: Context,
    private val items: List<WeeksData>,
    private val clickListener: (WeeksData) -> Unit
) : RecyclerView.Adapter<weeksListAdapter.ItemViewHolder>() {

    // Create a ViewHolder to hold the views of each item
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.days_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleTextView.text = currentItem.Day
        holder.itemView.setOnClickListener {
            clickListener(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
