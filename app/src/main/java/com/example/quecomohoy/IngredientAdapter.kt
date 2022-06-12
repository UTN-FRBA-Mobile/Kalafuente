package com.example.quecomohoy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.quecomohoy.data.model.Ingredient
import com.squareup.picasso.Picasso

class IngredientAdapter(private val dataSet: List<Ingredient>) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): IngredientViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return IngredientViewHolder(layoutInflater.inflate(R.layout.search_ingredient_item, viewGroup, false))
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(viewHolder: IngredientViewHolder, position: Int) {
        viewHolder.titleTextView.text = dataSet[position].name
        Picasso.get()
            .load(dataSet[position].picture)
            .fit()
            .into(viewHolder.imageView);

    }

    class IngredientViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView
        val imageView : ImageView
        init {
            titleTextView = view.findViewById(R.id.recipe_name)
            imageView = view.findViewById(R.id.recipe_pic)
        }
    }
}