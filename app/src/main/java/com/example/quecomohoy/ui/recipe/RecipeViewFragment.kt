package com.example.quecomohoy.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.quecomohoy.databinding.RecipeViewFragmentBinding
import com.squareup.picasso.Picasso


class RecipeViewFragment : Fragment() {
    private var _binding: RecipeViewFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = RecipeViewFragment()
    }

    private lateinit var viewModel: RecipeViewViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = RecipeViewFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecipeViewViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title: TextView = binding.recomendationTitle
        title.text = arguments?.getString("nameRecipe")
        Picasso.get()
            .load(arguments?.getString("img"))
            .fit()
            .into(binding.img);
    }

}