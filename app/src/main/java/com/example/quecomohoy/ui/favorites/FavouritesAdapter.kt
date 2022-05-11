package com.example.quecomohoy.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quecomohoy.R
import com.example.quecomohoy.data.model.Favourite
import com.squareup.picasso.Picasso

class FavouritesAdapter (private val dataset: List<Favourite>) :
    RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesAdapter.FavouritesViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.favourites_item, parent, false)

        return FavouritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val favourite = dataset[position]
        holder.titleTextView.text = favourite.name
        holder.authorTextView.text = "By " + favourite.authorName

        Picasso.get()
            .load(favourite.image)
            .fit()
            .into(holder.imageView);
    }

    override fun getItemCount() = dataset.size

    class FavouritesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val authorTextView: TextView = view.findViewById(R.id.authorTextView)
        val imageView : ImageView = view.findViewById(R.id.coverImageView)
    }
}