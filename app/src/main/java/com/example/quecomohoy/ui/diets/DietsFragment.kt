package com.example.quecomohoy.ui.diets

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quecomohoy.R
import com.example.quecomohoy.data.model.diet.*
import com.example.quecomohoy.data.repositories.DietRepository
import com.example.quecomohoy.databinding.FragmentDietListBinding
import com.example.quecomohoy.ui.diets.interfaces.OnClickDietListener
import java.lang.Exception

/**
 * A fragment representing a list of Items.
 */
class DietsFragment : Fragment() {

    private var _binding: FragmentDietListBinding? = null
    private val binding get() = _binding!!

    private lateinit var dietModel: DietViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDietListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dietModel = DietViewModel(DietRepository())
        val sp = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val userId = sp.getInt("userId", -1)

        dietModel.selectCurrentDiet(arguments?.getInt("dietID", 1)!!)

        dietModel.radioButtonId.observe(viewLifecycleOwner){
            binding.group.check(it)
        }

        binding.saveDietButton.setOnClickListener{
            dietModel.saveDiet(binding.group.checkedRadioButtonId,userId)
            findNavController().popBackStack()
        }
    }

}