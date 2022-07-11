package com.example.quecomohoy.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.quecomohoy.R
import com.example.quecomohoy.data.model.Ingredient
import com.example.quecomohoy.data.model.perfil.LIKED_INGREDIENTS
import com.example.quecomohoy.data.model.perfil.UNLIKED_INGREDIENTS
import com.example.quecomohoy.data.model.perfil.UserPreference
import com.example.quecomohoy.data.model.perfil.UserPreferences
import com.example.quecomohoy.databinding.FragmentUserIngredientsBinding
import com.example.quecomohoy.ui.*
import com.example.quecomohoy.ui.searchrecipes.INGREDIENTS_TAB
import com.example.quecomohoy.ui.searchrecipes.IngredientsFragment
import com.example.quecomohoy.ui.searchrecipes.RECIPES_TAB
import com.example.quecomohoy.ui.searchrecipes.RecipesFragment
import com.example.quecomohoy.ui.searchrecipes.adapters.IngredientsAdapter
import com.example.quecomohoy.ui.searchrecipes.adapters.SelectedIngredientAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable
import java.lang.Exception

class UserIngredientsFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentUserIngredientsBinding? = null
    private val binding get() = _binding!!
    private val ingredientViewModel: IngredientViewModel by viewModels(
        factoryProducer = { IngredientViewModelFactory() }
    )

    private val profileViewModel: ProfileViewModel by viewModels(
        factoryProducer = { ProfileViewModelFactory() },
        ownerProducer = { requireParentFragment() }
    )

    private var queryTextJob: Job? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pc = requireArguments().getInt("preferenceCode");
        val userPreference = requireArguments().getSerializable("userPreferences") as UserPreferences

        binding.searchView.isIconifiedByDefault = false
        binding.searchView.setOnQueryTextListener(this)
        binding.saveButton.setOnClickListener {
            val sp = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val userId = sp.getInt("userId", -1)
            val selectedIng = ingredientViewModel.getSelectedIngredients()
            when(pc){
                LIKED_INGREDIENTS -> {
                    userPreference.likedIngredients = selectedIng
                }
                UNLIKED_INGREDIENTS -> {
                    userPreference.unlikedIngredients = selectedIng
                }
                else -> throw Exception("")
            }
            profileViewModel.savePreferences(userId,userPreference)
            view.findNavController().popBackStack();
        }

        val selectedIngredients = when(pc){
            LIKED_INGREDIENTS -> userPreference.likedIngredients
            UNLIKED_INGREDIENTS -> userPreference.unlikedIngredients
            else -> emptyList()
        }

        showAddedIngredients(selectedIngredients.isNotEmpty())
        ingredientViewModel.addIngredients(selectedIngredients)

        val adpater = IngredientsAdapter { ingredient: Ingredient ->
            binding.verticalRecyclerView.isInvisible = true
            ingredientViewModel.addIngredient(ingredient)
            showAddedIngredients(true)
        }

        val addedIngredientsAdapter =
            SelectedIngredientAdapter(selectedIngredients) { index: Int ->
                ingredientViewModel.removeIngredient(index)
                showAddedIngredients(ingredientViewModel.hasSelectedIngredients())
            }

        binding.verticalRecyclerView.adapter = adpater
        binding.horizontalRecyclerView.adapter = addedIngredientsAdapter

        ingredientViewModel.ingredients.observe(viewLifecycleOwner) {
            val searchTerm = it.additionalData?.get("searchTerm") as String?
            when(it.status){
                Status.SUCCESS -> {
                    val ingredients = it.data.orEmpty()
                    binding.progress.isVisible = false
                    binding.notFound.isVisible = ingredients.isEmpty() && !searchTerm.isNullOrEmpty()
                    binding.verticalRecyclerView.isVisible = true
                    showAddedIngredients(ingredients.isEmpty()
                            && searchTerm.isNullOrEmpty()
                            && ingredientViewModel.hasSelectedIngredients())
                    adpater.updateData(ingredients)
                }
                Status.LOADING -> {
                    binding.progress.isVisible = true
                    binding.notFound.isVisible = false
                    binding.verticalRecyclerView.isVisible = false
                    showAddedIngredients( searchTerm.isNullOrEmpty()
                            && ingredientViewModel.hasSelectedIngredients())
                }
                Status.ERROR -> {
                    binding.progress.isVisible = false
                    binding.verticalRecyclerView.isVisible = false
                    Snackbar.make(view, "Hubo un error", Snackbar.LENGTH_SHORT)
                        .setAction("Reintentar"){
                            searchTerm?.let { s -> ingredientViewModel.getIngredientsByName(s) }
                        }.show()
                }
            }
        }

        ingredientViewModel.addedIngredient.observe(viewLifecycleOwner) {
            if (lifecycle.currentState != Lifecycle.State.STARTED) {
                addedIngredientsAdapter.addItem(it)
            }
        }
    }

    private fun showAddedIngredients(b: Boolean) {
        binding.selectedIngredients.isVisible = b
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