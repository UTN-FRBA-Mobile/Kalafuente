package com.example.quecomohoy

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        Log.d("image url---------",dataSet[position].image)
        Log.d("image url---------", viewHolder.imageView.toString())

        Glide
            .with(viewHolder.imageView.context)
            .load("https://i.blogs.es/5ee30a/istock-522189977/1366_2000.jpg")
            .centerCrop()
            .placeholder(R.drawable.logoqch)
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