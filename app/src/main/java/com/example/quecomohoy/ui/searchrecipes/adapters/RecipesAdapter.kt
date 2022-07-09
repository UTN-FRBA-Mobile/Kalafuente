package com.example.quecomohoy.ui.searchrecipes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.example.quecomohoy.R
import com.example.quecomohoy.data.model.recipe.Recipe
import com.example.quecomohoy.ui.listeners.RecipeListener
import com.squareup.picasso.Picasso

class RecipesAdapter(
    private val recipeListener: RecipeListener,
    recipes: List<Recipe> = emptyList()
) : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    val data: MutableList<Recipe> = listOf<Recipe>().toMutableList()

    init {
        data.addAll(recipes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipes_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = data[position]

        with(holder){
            itemView.setOnClickListener {
                recipeListener.onClickRecipe(recipe.id)
            }
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                recipeListener.onMarkAsFavourite(recipe.id, isChecked)
            }
            titleTextView.text = recipe.name
            authorTextView.text = itemView.context.getString(R.string.by_author, recipe.authorName)
            Picasso.get().load(recipe.picture).fit().into(imageView)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newData: List<Recipe>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val authorTextView: TextView = view.findViewById(R.id.authorTextView)
        val imageView: ImageView = view.findViewById(R.id.coverImageView)
        val checkBox: CheckBox = view.findViewById(R.id.checkbox)
        init {
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                /*
                if (isChecked) {
                    Toast.makeText(view.context, "Le diste like!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(view.context, "Le sacaste el like :c", Toast.LENGTH_SHORT).show()
                }*/
            }
        }

        fun fill(recipe: Recipe) {
            titleTextView.text = recipe.name
            authorTextView.text = itemView.context.getString(R.string.by_author, recipe.authorName)
            if (recipe.picture.isNotEmpty()) {
                Picasso.get().load(recipe.picture).fit().into(imageView)
            }
            checkBox.isChecked = recipe.isFavourite
        }
    }
}