package com.example.quecomohoy.ui.searchrecipes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quecomohoy.R
import com.example.quecomohoy.data.model.Ingredient
import com.squareup.picasso.Picasso

class SelectedIngredientAdapter(val onRemove: (Int) -> Unit) : RecyclerView.Adapter<SelectedIngredientAdapter.ViewHolder>() {
    val data: MutableList<Ingredient> = listOf<Ingredient>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.selected_ingredient_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = data[position]
        holder.fill(recipe)
        holder.picture.setOnClickListener{
            data.removeAt(holder.absoluteAdapterPosition)
            onRemove(holder.absoluteAdapterPosition)
            notifyItemRemoved(holder.absoluteAdapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newData: List<Ingredient>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun addItem(ingredient: Ingredient){
        data.add(ingredient)
        notifyItemInserted(itemCount-1)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var picture: ImageView = view.findViewById(R.id.picture)
        var name : TextView = view.findViewById(R.id.name)

        fun fill(ingredient: Ingredient) {
            name.text = ingredient.name
            if(ingredient.picture.isNotEmpty()){
                Picasso.get().load(ingredient.picture).fit().into(picture)
            }
        }
    }
}