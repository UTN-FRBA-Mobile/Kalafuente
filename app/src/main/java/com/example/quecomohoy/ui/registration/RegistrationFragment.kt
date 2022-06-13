package com.example.quecomohoy.ui.registration

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.quecomohoy.R
import com.example.quecomohoy.data.requests.UserSignupRequest
import com.example.quecomohoy.data.utils.AESEncyption
import com.example.quecomohoy.databinding.FragmentRegistrationBinding
import com.example.quecomohoy.ui.RecommendationViewModel
import com.example.quecomohoy.ui.RecommendationViewModelFactory
import com.example.quecomohoy.ui.Status
import com.example.quecomohoy.ui.login.LoginViewModel
import com.example.quecomohoy.ui.login.LoginViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null

    private val registrationViewModel: RegistrationViewModel by viewModels(factoryProducer = { RegistrationViewModelFactory() })

    private lateinit var loginViewModel: LoginViewModel
    private val recommendationsViewModel: RecommendationViewModel by viewModels(
        factoryProducer = { RecommendationViewModelFactory() },
        ownerProducer = { requireParentFragment() }
    )

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(
            "LOGINFRAGMENT----------------------------------------------------------------",
            "---------------------"
        )
        loginViewModel = ViewModelProvider(requireActivity(), LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        super.onViewCreated(view, savedInstanceState)

        val createAccountButton = binding.createAccountButton

        createAccountButton.setOnClickListener {
            val haveErrors = validateInputs()
            if (!haveErrors) {
                try {
                    registrationViewModel.signup(createUserRequest())
                } catch (e: Exception) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        registrationViewModel.user.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(context, "Bienvenido ${it.data?.username}", Toast.LENGTH_LONG)
                        .show()
                    loginViewModel.login(binding.userName.text.toString(), binding.password.text.toString())
                    view.findNavController().navigate(R.id.action_registrationFragment_to_recomendationsFragment)
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                }
            }
        }
    }

    private fun validateInputs() : Boolean{
        inputs().forEach {
            if(it.text.isNullOrEmpty()){
                it.error = "Completar"
            } else{
                it.error = null
            }
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString()).matches()){
            binding.email.error = "Mail invalido"
        }

        return inputs().any { !it.error.isNullOrEmpty() }
    }

    private fun inputs(): List<TextInputEditText> {
        return listOf(
            binding.name,
            binding.lastnames,
            binding.email,
            binding.password,
            binding.userName
        )
    }

    private fun createUserRequest(): UserSignupRequest {
        return UserSignupRequest(
            name = binding.name.text.toString(),
            lastName = binding.lastnames.text.toString(),
            email = binding.email.text.toString(),
            password = AESEncyption.encrypt(binding.password.text.toString()),
            userName = binding.userName.text.toString()
        )
    }

}