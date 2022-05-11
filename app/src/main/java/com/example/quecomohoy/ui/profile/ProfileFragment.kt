package com.example.quecomohoy.ui.profile

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quecomohoy.data.model.perfil.UserPreference
import com.example.quecomohoy.databinding.FragmentProfileBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {

    //private lateinit var registrationViewModel: RegistrationViewModel
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<UserPreferencesAdapater.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val profilePic = _binding?.profilePic
        val settings = _binding?.preferences

        layoutManager = LinearLayoutManager(context?.applicationContext)
        settings?.layoutManager = layoutManager
        val listOfSettings = getListOfUserPreferences();
        adapter = UserPreferencesAdapater(listOfSettings);
        settings?.adapter = adapter

        Picasso.get()
            .load("https://pbs.twimg.com/profile_images/1447703122927923206/2SNjVwEe_400x400.jpg")
            .into(profilePic, object : Callback{
                override fun onSuccess() {
                    Log.d(TAG, "success")
                }

                override fun onError(e: Exception?) {
                    Log.e(TAG, "error", e)
                }

            })
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getListOfUserPreferences() : List<UserPreference>{
       return listOf(
            UserPreference("Dietas", "Vegetariano"),
            UserPreference("Mis ingredientes", "Los ingredientes que me gustan"),
            UserPreference("Alergias", null),
            UserPreference("Ingredientes que no me gustan", "Salmón, Huevos, Tomate")
        )
    }
}