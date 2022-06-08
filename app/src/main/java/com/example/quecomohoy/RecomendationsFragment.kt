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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quecomohoy.data.model.Recommendation
import com.example.quecomohoy.ui.login.LoginViewModel
import com.example.quecomohoy.ui.login.LoginViewModelFactory


class RecomendationsFragment : Fragment() {
    private var _binding: FragmentRecomendationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel

    val recommendationsList : List<Recommendation> = listOf(
        Recommendation(1, "Omelette", "https://viapais.com.ar/resizer/mUQiFA14EV_X7bln_vY2CaTJ6V4=/982x551/smart/cloudfront-us-east-1.images.arcpublishing.com/grupoclarin/GIYWKYLGGVRTQNBYHA3TCOBXGU.jpg"),
        Recommendation(2, "Espinacas a la crema", "https://dam.cocinafacil.com.mx/wp-content/uploads/2019/03/espinacas-a-la-crema.png"),
        Recommendation(3, "Pancito", "https://i0.wp.com/blog.marianlaquecocina.com/wp-content/uploads/2018/04/20180416_144118.jpg")
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

        binding.rvRecommendation.hasFixedSize()
        binding.rvRecommendation.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecommendation.adapter = RecommendationsAdapter(recommendationsList, findNavController())
    }


}