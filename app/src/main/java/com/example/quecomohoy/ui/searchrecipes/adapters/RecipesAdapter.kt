package com.example.quecomohoy.ui.searchrecipes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quecomohoy.R
import com.example.quecomohoy.data.model.recipe.Recipe
import com.squareup.picasso.Picasso

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    val data : MutableList<Recipe> = listOf<Recipe>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_recipe_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = data[position]
        holder.fill(recipe)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newData: List<Recipe>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var name: TextView
        var picture: ImageView

        init {
            name = view.findViewById(R.id.recipe_name)
            picture = view.findViewById(R.id.recipe_pic)
            view.setOnClickListener {
                Toast.makeText(view.context, name.text, Toast.LENGTH_SHORT).show()
            }
        }

        fun fill(recipe : Recipe){
            name.text = recipe.name
            Picasso.get().load(recipe.picturePath).fit().into(picture)
        }
    }
}