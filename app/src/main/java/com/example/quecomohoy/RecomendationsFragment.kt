package com.example.quecomohoy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.quecomohoy.databinding.FragmentRecomendationsBinding
import android.util.Log
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quecomohoy.data.model.Recommendation
import com.example.quecomohoy.ui.*
import com.example.quecomohoy.ui.login.LoginViewModel
import com.example.quecomohoy.ui.login.LoginViewModelFactory
import com.google.android.material.snackbar.Snackbar


class RecomendationsFragment : Fragment() {
    private var _binding: FragmentRecomendationsBinding? = null
    private val binding get() = _binding!!
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

        _binding = FragmentRecomendationsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("Volvi a entrar a on View Created Recomendations Fragment","")
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(requireActivity(), LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.userInformation.observe(viewLifecycleOwner,
            Observer {userInformation ->
                     if(userInformation.displayName == ""){
                    val action = R.id.action_recomendationsFragment_to_loginFragment
                    findNavController().navigate(action)
                } else{
                  //  binding.userName.setText("Recomendaciones para " + userInformation.displayName)
                }
            })
        recommendationsViewModel.getRecommendationsByUser(2)
        recommendationsViewModel.recommendations.observe(viewLifecycleOwner) {
            when(it.status){
                Status.SUCCESS -> {
                    val recommendations = it.data.orEmpty()
                    binding.rvRecommendation.hasFixedSize()
                    binding.rvRecommendation.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvRecommendation.adapter = RecommendationsAdapter(recommendations, findNavController())
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
}