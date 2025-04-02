package com.example.Exercises
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ajfit.R
import com.example.roomDB.ExerciseInputText

class ExerciseListAdapter(
    private val context: Context,
    private var items: List<ExerciseInputText>,
    private val clickListener: (ExerciseInputText) -> Unit
) : RecyclerView.Adapter<ExerciseListAdapter.ItemViewHolder>() {

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
        holder.titleTextView.text = currentItem.name
        holder.itemView.setOnClickListener {
            clickListener(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
