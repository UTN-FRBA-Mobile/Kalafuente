package com.example.quecomohoy.ui.registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.quecomohoy.databinding.FragmentRegistrationBinding

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

    private lateinit var registrationViewModel: RegistrationViewModel
    private var _binding: FragmentRegistrationBinding? = null

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
        Log.d("LOGINFRAGMENT----------------------------------------------------------------","---------------------")

        super.onViewCreated(view, savedInstanceState)
        registrationViewModel = ViewModelProvider(this, RegistrationViewModelFactory())
            .get(RegistrationViewModel::class.java)

        val nameEditText = binding.name
        val lastnameEditText = binding.lastnames
        val mailEditText = binding.email
        val passwordEditText = binding.password

        val loginButton = binding.createAccountButton

        loginButton.setOnClickListener {
            val text = "Welcome " + nameEditText.text.toString() + " " + lastnameEditText.text.toString() + "!"
            val duration = Toast.LENGTH_SHORT
            val appContext = context?.applicationContext

            val toast = Toast.makeText(appContext, text, duration)
            toast.show()
        }
    }

}