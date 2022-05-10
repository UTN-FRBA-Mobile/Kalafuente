package com.example.quecomohoy

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quecomohoy.data.model.Recommendation
import com.squareup.picasso.Picasso

class RecommendationsAdapter(private val dataSet: List<Recommendation>) :
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
    }

    class RecommendationsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView
        val imageView : ImageView

        init {
            titleTextView = view.findViewById(R.id.recomendationTitle)
            imageView = view.findViewById(R.id.img)
        }
    }
}