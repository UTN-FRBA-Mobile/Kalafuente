package com.example.quecomohoy.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.quecomohoy.R
import com.example.quecomohoy.databinding.FragmentUserIngredientsBinding
import com.example.quecomohoy.ui.IngredientViewModel
import com.example.quecomohoy.ui.IngredientViewModelFactory
import com.example.quecomohoy.ui.RecipeViewModel
import com.example.quecomohoy.ui.RecipeViewModelFactory
import com.example.quecomohoy.ui.searchrecipes.INGREDIENTS_TAB
import com.example.quecomohoy.ui.searchrecipes.IngredientsFragment
import com.example.quecomohoy.ui.searchrecipes.RECIPES_TAB
import com.example.quecomohoy.ui.searchrecipes.RecipesFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserIngredientsFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentUserIngredientsBinding? = null
    private val binding get() = _binding!!
    private val ingredientViewModel: IngredientViewModel by viewModels(factoryProducer = { IngredientViewModelFactory() })

    private var queryTextJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var viewType : Int = requireArguments().getInt("viewType");
        binding.searchView.isIconifiedByDefault = false
        binding.searchView.setOnQueryTextListener(this)
        binding.saveButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putIntArray("ids", ingredientViewModel.getSelectedIngredientsIds())
            //TODO GUARDAR
            view.findNavController().popBackStack();
        }

        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.add(R.id.userIngredientsFragment,IngredientsFragment())
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("text", binding.searchView.query.toString())
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        binding.searchView.clearFocus()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean { queryTextJob?.cancel()
        queryTextJob = lifecycleScope.launch {
            if (!newText.isNullOrEmpty()) {
                delay(1000)
            }
            ingredientViewModel.getIngredientsByName(newText.orEmpty())
        }
        return true
    }
}