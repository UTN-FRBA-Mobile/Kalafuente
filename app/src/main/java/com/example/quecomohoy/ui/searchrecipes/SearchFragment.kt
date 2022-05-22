package com.example.quecomohoy.ui.searchrecipes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.quecomohoy.R
import com.example.quecomohoy.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModels(factoryProducer = { SearchViewModelFactory() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPagerAdapter = ViewPagerAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = viewPagerAdapter
        val tabLayout = binding.tabLayout

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                binding.searchRecipeLayout.hint = when (tab.position) {
                    0 -> getString(R.string.write_recipe_name)
                    1 -> getString(R.string.write_an_ingredient)
                    else -> throw Exception("No deberías estara acá")
                }
                binding.searchRecipeInput.text?.clear()
                binding.searchRecipeInput.clearFocus()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        viewPager.isUserInputEnabled = false;

        binding.searchRecipeInput.doAfterTextChanged {
            when (viewPager.currentItem) {
                0 -> searchViewModel.findRecipes(it.toString())
                1 -> {
                    searchViewModel.findIngredients(it.toString())
                    viewPagerAdapter.ingredientsFragment.showAddedIngredients(it.isNullOrEmpty())
                }
                else -> throw Exception("No deberías estara acá")
            }
        }

        searchViewModel.addedIngredient.observe(viewLifecycleOwner){
            binding.searchRecipeInput.text = null
            binding.searchRecipeInput.hideKeyboard()
        }
    }

    class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        lateinit var recipesFragment: RecipesFragment
        lateinit var ingredientsFragment: IngredientsFragment

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            recipesFragment = RecipesFragment()
            ingredientsFragment = IngredientsFragment()
            return when (position) {
                0 -> recipesFragment
                1 -> ingredientsFragment
                else -> throw Exception("No deberías estar acá")
            }
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}