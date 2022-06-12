package com.example.quecomohoy.ui.searchrecipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.quecomohoy.R
import com.example.quecomohoy.databinding.FragmentSearchBinding
import com.example.quecomohoy.ui.IngredientViewModel
import com.example.quecomohoy.ui.IngredientViewModelFactory
import com.example.quecomohoy.ui.RecipeViewModel
import com.example.quecomohoy.ui.RecipeViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


const val RECIPES_TAB = 0
const val INGREDIENTS_TAB = 1

class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var viewPager: ViewPager2
    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    private val recipeViewModel: RecipeViewModel by viewModels(factoryProducer = { RecipeViewModelFactory() })
    private val ingredientViewModel: IngredientViewModel by viewModels(factoryProducer = { IngredientViewModelFactory() })

    private var queryTextJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPager = binding.viewPager
        viewPager.adapter = viewPagerAdapter
        val tabLayout = binding.tabLayout

        binding.searchView.isIconifiedByDefault = false
        binding.searchView.setOnQueryTextListener(this)

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                binding.searchView.queryHint = when (tab.position) {
                    RECIPES_TAB -> getString(R.string.write_recipe_name)
                    INGREDIENTS_TAB -> getString(R.string.write_an_ingredient)
                    else -> throw Exception("No deberías estara acá")
                }
                binding.startCookingButton.isVisible =
                    tab.position == INGREDIENTS_TAB && ingredientViewModel.hasSelectedIngredients()
                binding.searchView.setQuery(null, false)
                binding.searchView.clearFocus()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    RECIPES_TAB -> ingredientViewModel.cleanIngredients()
                    INGREDIENTS_TAB -> recipeViewModel.cleanRecipes()
                    else -> throw Exception("No deberías estara acá")
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        binding.startCookingButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putIntArray("ids", ingredientViewModel.getSelectedIngredientsIds())
            view.findNavController().navigate(R.id.action_searchFragment_to_recipesFragment, bundle)
        }

       binding.scanIngredientsButton.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_searchFragment_to_scanIngredientsFragment, Bundle())
        }

        viewPager.isUserInputEnabled = false

        ingredientViewModel.addedIngredient.observe(viewLifecycleOwner) {
            if (viewPager.currentItem == INGREDIENTS_TAB) {
                binding.searchView.setQuery(null, false)
            }
        }

        ingredientViewModel.selectedIngredients.observe(viewLifecycleOwner) {
            binding.startCookingButton.isVisible = !it.isNullOrEmpty()
        }
    }

    class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                RECIPES_TAB -> RecipesFragment.newInstance(R.id.action_searchFragment_to_recipeViewFragment)
                INGREDIENTS_TAB -> IngredientsFragment()
                else -> throw Exception("No deberías estar acá")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("text", binding.searchView.query.toString())
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        binding.searchView.clearFocus()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        queryTextJob?.cancel()
        queryTextJob = lifecycleScope.launch {
            if (!newText.isNullOrEmpty()) {
                delay(1000)
            }
            when (viewPager.currentItem) {
                RECIPES_TAB -> recipeViewModel.getRecipesByName(newText.orEmpty())
                INGREDIENTS_TAB -> ingredientViewModel.getIngredientsByName(newText.orEmpty())
                else -> throw Exception("No deberías estara acá")
            }
        }
        return true
    }

}