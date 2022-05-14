package com.example.quecomohoy.ui.searchrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quecomohoy.databinding.FragmentRecipesBinding
import com.example.quecomohoy.ui.searchrecipes.adapters.RecipesAdapter

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null

    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels({requireParentFragment()}, {SearchViewModelFactory()})

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adpater = RecipesAdapter()
        binding.recipesRecycler.adapter = adpater

        viewModel.recipes.observe(viewLifecycleOwner){
            adpater.updateData(it);
        }
    }

}