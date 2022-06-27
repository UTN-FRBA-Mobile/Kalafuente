package com.example.quecomohoy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.quecomohoy.data.model.Recommendation
import com.example.quecomohoy.ui.listeners.RecipeListener
import com.squareup.picasso.Picasso

class RecommendationsAdapter(
    private val recipeListener: RecipeListener,
    private val dataSet: List<Recommendation>
) :
    RecyclerView.Adapter<RecommendationsAdapter.RecommendationsViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): RecommendationsViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return RecommendationsViewHolder(
            layoutInflater.inflate(
                R.layout.recomendation_item,
                viewGroup,
                false
            )
        )
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(viewHolder: RecommendationsViewHolder, position: Int) {
        val recommendation = dataSet[position]
        with(viewHolder) {
            titleTextView.text = recommendation.name
            Picasso.get()
                .load(recommendation.picture)
                .fit()
                .into(imageView);
            cardContainer.setOnClickListener {
                recipeListener.onClickRecipe(recommendation.id)
            }
            checkBox.setOnCheckedChangeListener { _, mark ->
                recipeListener.onMarkAsFavourite(recommendation.id, mark)
            }
        }
    }

    class RecommendationsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView
        val imageView: ImageView
        val cardContainer: RelativeLayout
        val checkBox: CheckBox

        init {
            titleTextView = view.findViewById(R.id.recomendationTitle)
            imageView = view.findViewById(R.id.img)
            cardContainer = view.findViewById(R.id.cardContainer)
            checkBox = view.findViewById(R.id.checkBox)
        }
    }
}