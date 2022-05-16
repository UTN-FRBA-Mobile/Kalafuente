package com.example.quecomohoy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.quecomohoy.data.model.Recommendation
import com.squareup.picasso.Picasso

class RecommendationsAdapter(private val dataSet: List<Recommendation>,  val navController: NavController) :
    RecyclerView.Adapter<RecommendationsAdapter.RecommendationsViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecommendationsViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return RecommendationsViewHolder(layoutInflater.inflate(R.layout.recomendation_item, viewGroup, false))
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(viewHolder: RecommendationsViewHolder, position: Int) {
        viewHolder.titleTextView.text = dataSet[position].name
        Picasso.get()
            .load(dataSet[position].image)
            .fit()
            .into(viewHolder.imageView);

        viewHolder.cardContainer.setOnClickListener {
                val action = R.id.action_recomendationsFragment_to_recipeViewFragment
                val bundle = bundleOf("nameRecipe" to dataSet[position].name,
                "img" to dataSet[position].image)
                navController.navigate(action, bundle)
        }
    }

    class RecommendationsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView
        val imageView : ImageView
        val cardContainer : RelativeLayout
        init {
            titleTextView = view.findViewById(R.id.recomendationTitle)
            imageView = view.findViewById(R.id.img)
            cardContainer = view.findViewById(R.id.cardContainer)
        }
    }
}