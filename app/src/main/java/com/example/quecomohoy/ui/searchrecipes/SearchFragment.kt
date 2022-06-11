package com.example.quecomohoy.ui.searchrecipes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.quecomohoy.databinding.FragmentSearchBinding
import com.example.quecomohoy.ui.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


const val RECIPES_TAB = 0
const val INGREDIENTS_TAB = 1

class SearchFragment : Fragment() {

    private var subscription: Disposable? = null
    private lateinit var viewPager: ViewPager2
    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    private val recipeViewModel: RecipeViewModel by viewModels(factoryProducer = { RecipeViewModelFactory() })
    private val ingredientViewModel: IngredientViewModel by viewModels(factoryProducer = { IngredientViewModelFactory() })

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

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                binding.searchRecipeLayout.hint = when (tab.position) {
                    RECIPES_TAB -> getString(R.string.write_recipe_name)
                    INGREDIENTS_TAB -> getString(R.string.write_an_ingredient)
                    else -> throw Exception("No deberías estara acá")
                }
                binding.startCookingButton.isVisible = tab.position == INGREDIENTS_TAB
                binding.searchRecipeInput.text?.clear()
                binding.searchRecipeInput.clearFocus()
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
            view.findNavController().navigate(R.id.action_searchFragment_to_scanIngredientsFragment, Bundle())
        }

        viewPager.isUserInputEnabled = false

        ingredientViewModel.addedIngredient.observe(viewLifecycleOwner) {
            if (viewPager.currentItem == INGREDIENTS_TAB) {
                binding.searchRecipeInput.text = null
                binding.searchRecipeInput.hideKeyboard()
            }
        }

        ingredientViewModel.selectedIngredients.observe(viewLifecycleOwner){
            binding.startCookingButton.isVisible = !it.isNullOrEmpty()
        }

        binding.searchRecipeInput.setOnFocusChangeListener { view, b ->
            binding.startCookingButton.isVisible = !b
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

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("text", binding.searchRecipeInput.text.toString())
    }

    override fun onPause() {
        subscription?.dispose()
        super.onPause()
    }

    override fun onResume() {
        subscription = RxTextView.afterTextChangeEvents(binding.searchRecipeInput)
            .debounce(1, TimeUnit.SECONDS)
            .subscribe {
                val text = it.editable().toString()
                if (it.view().isFocused) {
                    when (viewPager.currentItem) {
                        RECIPES_TAB -> recipeViewModel.getRecipesByName(text)
                        INGREDIENTS_TAB -> ingredientViewModel.getIngredientsByName(text)
                        else -> throw Exception("No deberías estara acá")
                    }
                }
            }
        super.onResume()
    }

}