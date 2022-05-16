package com.example.quecomohoy.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.quecomohoy.R
import com.example.quecomohoy.RecommendationsAdapter
import com.example.quecomohoy.data.model.Ingredient
import com.example.quecomohoy.databinding.RecipeViewFragmentBinding
import com.example.quecomohoy.ui.searchrecipes.IngredientsFragment
import com.example.quecomohoy.ui.searchrecipes.RecipesFragment
import com.example.quecomohoy.ui.searchrecipes.SearchFragment
import com.example.quecomohoy.IngredientAdapter
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso


class RecipeViewFragment : Fragment() {
    private var _binding: RecipeViewFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = RecipeViewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = RecipeViewFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title: TextView = binding.recomendationTitle

        binding.rvIngredientItem.hasFixedSize()
        binding.rvIngredientItem.layoutManager = LinearLayoutManager(requireContext())


        val ingredients : List<Ingredient> = listOf(
            Ingredient(1, "Manzana", "https://www.cuerpomente.com/medio/2020/11/10/manzana_a1c5bdb0_1200x1200.jpg"),
            Ingredient(2, "Pollo", "https://www.carnave.com.ar/wp-content/uploads/2020/05/Pollo-entero.jpg"),
            Ingredient(3, "Huevo", "https://www.collinsdictionary.com/images/full/eggs_110803370_1000.jpg"),
            Ingredient(4, "Mostaza","https://cdn2.cocinadelirante.com/sites/default/files/styles/gallerie/public/images/2017/04/most.jpg"),
            Ingredient(5, "Fideos", "https://jumboargentina.vtexassets.com/arquivos/ids/209822/Fideo-Molto-Guiseros-Fideos-Guisero-Molto-500-Gr-1-46224.jpg?v=636383732923400000")
        )

        val ingredientsAdapter = IngredientAdapter(ingredients)
        binding.rvIngredientItem.adapter = ingredientsAdapter;
        title.text = arguments?.getString("nameRecipe")
        Picasso.get()
            .load(arguments?.getString("img"))
            .fit()
            .into(binding.img);

    }

}