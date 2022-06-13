package com.example.quecomohoy.ui.profile

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quecomohoy.R
import com.example.quecomohoy.data.model.perfil.UserPreference
import com.example.quecomohoy.databinding.FragmentProfileBinding
import com.example.quecomohoy.ui.RecommendationViewModel
import com.example.quecomohoy.ui.RecommendationViewModelFactory
import com.example.quecomohoy.ui.login.LoginViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quecomohoy.ui.login.LoginViewModelFactory


class ProfileFragment : Fragment() {

    //private lateinit var registrationViewModel: RegistrationViewModel
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<UserProfilePreferencesAdapater.ViewHolder>? = null

    private lateinit var loginViewModel: LoginViewModel
    private val recommendationsViewModel: RecommendationViewModel by viewModels(
        factoryProducer = { RecommendationViewModelFactory() },
        ownerProducer = { requireParentFragment() }
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val profilePic = _binding?.profilePic
        val settings = _binding?.preferences

        loginViewModel = ViewModelProvider(requireActivity(), LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        layoutManager = LinearLayoutManager(context?.applicationContext)
        settings?.layoutManager = layoutManager
        val listOfSettings = getListOfUserPreferences();
        adapter = UserProfilePreferencesAdapater(listOfSettings);
        settings?.adapter = adapter

        loginViewModel.userInformation.observe(viewLifecycleOwner,
            Observer {userInformation ->
                if(userInformation.displayName != ""){
                    binding.name.text = userInformation.displayName
                    binding.username.text = userInformation.userName
                    Picasso.get()
                        .load(userInformation.image)
                        .into(profilePic, object : Callback {
                            override fun onSuccess() {
                                Log.d(TAG, "success")
                            }
                            override fun onError(e: Exception?) {
                                Log.e(TAG, "error", e)
                            }
                        })
                }
            })
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getListOfUserPreferences(): List<UserPreference> {
        return listOf(
            UserPreference(1, "Dietas", "Vegetariano"),
            UserPreference(2, "Mis ingredientes", "Los ingredientes que me gustan"),
            UserPreference(3, "Alergias", null),
            UserPreference(
                4,
                "Ingredientes que no me gustan",
                "Salmón, Huevos, Tomate, Lechuga, Carnes rojas, Pollos"
            )
        )
    }
}