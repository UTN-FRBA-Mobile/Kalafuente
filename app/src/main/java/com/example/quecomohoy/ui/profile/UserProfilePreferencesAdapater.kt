package com.example.quecomohoy.ui.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quecomohoy.R
import com.example.quecomohoy.data.model.perfil.UserPreference
import com.example.quecomohoy.data.model.perfil.UserPreferenceTypes
import com.example.quecomohoy.ui.listeners.PreferenceListener

class UserProfilePreferencesAdapater(val preferenceListner: PreferenceListener, val preferences: List<UserPreference>) :
    RecyclerView.Adapter<UserProfilePreferencesAdapater.ViewHolder>() {

    private var context: Context? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init {
            itemImage = itemView.findViewById(R.id.imageView)
            itemTitle = itemView.findViewById(R.id.title)
            itemDetail = itemView.findViewById(R.id.subtitle)
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): UserProfilePreferencesAdapater.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.user_preference_card_layout, viewGroup, false)
        context = viewGroup.context;
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserProfilePreferencesAdapater.ViewHolder, position: Int) {
        val userPreference = preferences[position]
        holder.itemTitle.text = userPreference.name
        if(!userPreference.value.isNullOrEmpty()){
            holder.itemDetail.text = userPreference.value
        } else{
            holder.itemDetail.visibility = View.GONE
        }
        holder.itemImage.setImageResource(R.drawable.ic_pencil)

        holder.itemImage.setOnClickListener {
            preferenceListner.onPreferenceClick(userPreference)
        }
    }

    override fun getItemCount(): Int {
        return preferences.size
    }
}