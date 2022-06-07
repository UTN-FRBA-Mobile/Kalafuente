package com.example.quecomohoy.ui.recipe;
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quecomohoy.R

class StepsAdapter(private val dataSet: List<String>) : RecyclerView.Adapter<StepsAdapter.StepsViewHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): StepsViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return StepsViewHolder(layoutInflater.inflate(R.layout.step_item, viewGroup, false))
        }

        override fun getItemCount() = dataSet.size

        override fun onBindViewHolder(viewHolder: StepsViewHolder, position: Int) {
                viewHolder.text.text = dataSet[position]
                viewHolder.index.text = (position + 1).toString();
        }

        class StepsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
                val text: TextView
                val index: TextView
                init {
                        text = view.findViewById(R.id.step_text)
                        index = view.findViewById(R.id.step_index)
                }
        }
}