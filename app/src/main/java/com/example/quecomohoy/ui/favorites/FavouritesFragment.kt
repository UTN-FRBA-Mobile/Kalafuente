package com.example.quecomohoy.ui.favorites

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quecomohoy.R
import com.example.quecomohoy.databinding.FragmentFavouritesBinding
import com.example.quecomohoy.ui.Status
import com.example.quecomohoy.ui.listeners.RecipeListener
import com.example.quecomohoy.ui.login.LoginViewModel
import com.example.quecomohoy.ui.login.LoginViewModelFactory
import com.example.quecomohoy.ui.searchrecipes.adapters.RecipesAdapter
import com.google.android.material.snackbar.Snackbar

class FavouritesFragment : Fragment(), RecipeListener {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel
    private val favouritesViewModel: FavouritesViewModel by viewModels(
    factoryProducer = { FavouritesViewModelFactory() },
    ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(requireActivity(), LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.userInformation.observe(viewLifecycleOwner,
            Observer {userInformation ->
                if(userInformation.displayName == ""){
                    val action = R.id.action_recomendationsFragment_to_loginFragment
                    findNavController().navigate(action)
                } else{
                    favouritesViewModel.getFavouritesByUser(userInformation.id)
                }
            })


        val viewManager = LinearLayoutManager(this.context)

        favouritesViewModel.favourites.observe(viewLifecycleOwner) {
            when(it.status){
                Status.SUCCESS -> {
                    val recipes = it.data.orEmpty()
                    if (recipes.isEmpty()) {
                        binding.emptyResultsLabel.visibility = View.VISIBLE
                    } else {
                        binding.emptyResultsLabel.visibility = View.GONE
                    }

                    val viewAdapter = RecipesAdapter(this,
                        recipes = recipes
                    )

                    binding.favoritesRV.apply {
                        layoutManager = viewManager
                        adapter = viewAdapter
                    }
                }
                Status.LOADING -> {
                    //TODO
                }
                Status.ERROR -> {
                    Snackbar.make(view, "Hubo un error", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onMarkAsFavourite(recipeId: Int, marked: Boolean) {
        val sp = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val userId = sp.getInt("userId", -1)
        favouritesViewModel.markAsFavourite(recipeId, userId, marked)
    }

    override fun onClickRecipe(recipeId: Int) {
        val args = Bundle()
        args.putInt("id", recipeId)
        findNavController().navigate(R.id.action_favouritesFragment_to_recipeViewFragment, args)
    }
}