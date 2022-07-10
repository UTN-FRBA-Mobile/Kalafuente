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
import com.example.quecomohoy.data.model.perfil.UserPreferences
import com.example.quecomohoy.databinding.FragmentProfileBinding
import com.example.quecomohoy.ui.login.LoginViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import androidx.lifecycle.ViewModelProvider
import com.example.quecomohoy.data.model.perfil.UserPreference
import com.example.quecomohoy.ui.Status
import com.example.quecomohoy.ui.listeners.PreferenceListener
import com.example.quecomohoy.ui.login.LoginViewModelFactory


class ProfileFragment : Fragment(), PreferenceListener {
    //private lateinit var registrationViewModel: RegistrationViewModel
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<UserProfilePreferencesAdapater.ViewHolder>? = null

    private lateinit var loginViewModel: LoginViewModel

    private val profileViewModel: ProfileViewModel by viewModels(
        factoryProducer = { ProfileViewModelFactory() },
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

        getListOfUserPreferences()
        loginViewModel.userInformation.observe(
            viewLifecycleOwner
        ) { userInformation ->
            if (userInformation.displayName != "") {
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
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun mapToUserPreferenceList(userPreferences: UserPreferences): List<UserPreference>{
        val diet = UserPreference(1, "Dieta", userPreferences.diet.name)
        val likedIngredients = UserPreference(2, "Ingredientes que me gustan", userPreferences.likedIngredients.joinToString(separator = ", ", transform = {
            it.name
        }))
        val unlikedIngredients = UserPreference(2, "Ingredientes que no me gustan", userPreferences.likedIngredients.joinToString(separator = ", ", transform = {
            it.name
        }))
        return listOf(diet,likedIngredients,unlikedIngredients)
    }

    private fun getListOfUserPreferences() {
        val settings = _binding?.preferences

        profileViewModel.getPreferencesByUserId(1)
        profileViewModel.profilePreferences.observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    if(it.data != null){
                        adapter = UserProfilePreferencesAdapater(this,
                            mapToUserPreferenceList(it.data)
                        );
                        settings?.adapter = adapter
                    }
                }
                Status.LOADING -> {
                    //TODO
                }
                Status.ERROR -> {
                    //TODO
                }
            }
        }
    }

    override fun onPreferenceClick(userPreference: UserPreference) {
        val args = Bundle()
        args.putString("label", userPreference.name)
        findNavController().navigate(R.id.action_profileFragment_to_preferencesFragment, args)
    }
}