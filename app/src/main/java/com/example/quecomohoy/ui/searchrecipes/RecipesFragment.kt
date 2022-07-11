package com.example.quecomohoy.ui.searchrecipes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quecomohoy.R
import com.example.quecomohoy.databinding.FragmentRecipesBinding
import com.example.quecomohoy.ui.RecipeViewModel
import com.example.quecomohoy.ui.RecipeViewModelFactory
import com.example.quecomohoy.ui.Status
import com.example.quecomohoy.ui.favorites.FavouritesViewModel
import com.example.quecomohoy.ui.favorites.FavouritesViewModelFactory
import com.example.quecomohoy.ui.listeners.RecipeListener
import com.example.quecomohoy.ui.searchrecipes.adapters.RecipesAdapter
import com.google.android.material.snackbar.Snackbar

class RecipesFragment : Fragment(), RecipeListener {

    private var _binding: FragmentRecipesBinding? = null

    private val binding get() = _binding!!

    private val recipeViewModel: RecipeViewModel by viewModels(
        { requireParentFragment() },
        { RecipeViewModelFactory() }
    )

    private val favouriteViewModel : FavouritesViewModel by viewModels(factoryProducer = {FavouritesViewModelFactory()})

    companion object {
        const val NAV_ACTION_ID = "navigationActionId"
        fun newInstance(@IdRes navigationActionId: Int): RecipesFragment {
            val fragment = RecipesFragment()
            val args = Bundle()
            args.putInt("navigationActionId", navigationActionId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = RecipesAdapter(this)

        binding.recipesRecycler.adapter = adapter

        recipeViewModel.recipes.observe(viewLifecycleOwner) {
            val searchTerm = (it.additionalData?.get("searchTerm") as String?) ?: arguments?.getString("searchTerm")
            when(it.status){
                Status.SUCCESS -> {
                    binding.recipesRecycler.isInvisible = it.data.isNullOrEmpty()
                    binding.progress.isVisible = false
                    binding.notFound.isVisible = it.data.isNullOrEmpty() && !searchTerm.isNullOrEmpty()
                    adapter.updateData(it.data.orEmpty())
                }
                Status.LOADING ->{
                    binding.recipesRecycler.isInvisible = true
                    binding.progress.isVisible = true
                }
                Status.ERROR -> {
                    binding.progress.isInvisible = true
                    binding.progress.isInvisible = true
                    Snackbar.make(view, "Hubo un error", Snackbar.LENGTH_SHORT)
                        .setAction("Reintentar"){
                            searchTerm?.let { s -> recipeViewModel.getRecipesByName(s) }
                        }.show()
                }
            }

        }

        val searchTerm = arguments?.getString("searchTerm")

        searchTerm?.also {
            recipeViewModel.getRecipesByName(it)
        }

        val ingredientIds = arguments?.getIntArray("ids")?.toList()

        ingredientIds?.also {
            recipeViewModel.getRecipesByIngredients(it)
        }
    }

    override fun onMarkAsFavourite(recipeId: Int, marked: Boolean) {
        val sp = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val userId = sp.getInt("userId", -1)
        favouriteViewModel.markAsFavourite(recipeId, userId, marked);

    }

    override fun onClickRecipe(recipeId: Int) {
        val navActionId = arguments?.getInt(NAV_ACTION_ID, R.id.action_recipesFragment_to_recipeViewFragment)
        if (navActionId != null) {
            val args = Bundle()
            args.putInt("id", recipeId)
            findNavController().navigate(navActionId, args)
        }
    }
}