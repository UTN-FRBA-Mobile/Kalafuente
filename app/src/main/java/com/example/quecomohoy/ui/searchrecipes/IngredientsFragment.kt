package com.example.quecomohoy.ui.searchrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quecomohoy.R
import com.example.quecomohoy.databinding.FragmentRecipesBinding
import com.example.quecomohoy.ui.searchrecipes.adapters.IngredientsAdapter
import com.example.quecomohoy.ui.searchrecipes.adapters.RecipesAdapter

class IngredientsFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null

    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels({requireParentFragment()}, {SearchViewModelFactory()})

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adpater = IngredientsAdapter()
        binding.recipesRecycler.adapter = adpater

        viewModel.ingredients.observe(viewLifecycleOwner){
            adpater.updateData(it);
        }
    }

}