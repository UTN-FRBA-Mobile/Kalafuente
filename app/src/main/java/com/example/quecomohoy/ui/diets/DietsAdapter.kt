package com.example.quecomohoy.ui.diets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quecomohoy.R
import com.example.quecomohoy.data.model.diet.Diet
import com.example.quecomohoy.ui.diets.interfaces.OnClickDietListener


class DietsAdapter(
    private val values: List<Diet>,
    private val listener : OnClickDietListener
) : RecyclerView.Adapter<DietsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.diet_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.fill(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(dietView: View) : RecyclerView.ViewHolder(dietView) {
        var title: TextView
        var description: TextView
        var radioButton : RadioButton

        init {
            title = dietView.findViewById(R.id.dietName)
            description = dietView.findViewById(R.id.dietDescription)
            radioButton = dietView.findViewById(R.id.dietRadioButton)

            dietView.setOnClickListener {
                radioButton.isChecked = !radioButton.isChecked
                listener.onClick(bindingAdapterPosition)
            }
        }

        fun fill(diet : Diet){
            title.text = diet.name;
            description.text = diet.descrition
            radioButton.isChecked = diet.selected
        }
    }

}