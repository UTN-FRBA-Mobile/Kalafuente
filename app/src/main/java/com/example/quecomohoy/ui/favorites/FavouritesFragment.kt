package com.example.quecomohoy.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quecomohoy.data.model.Favourite
import com.example.quecomohoy.data.model.Recommendation
import com.example.quecomohoy.data.model.recipe.Recipe
import com.example.quecomohoy.databinding.FragmentFavouritesBinding
import com.example.quecomohoy.databinding.FragmentRecomendationsBinding
import com.example.quecomohoy.ui.login.LoginViewModel
import com.example.quecomohoy.ui.searchrecipes.adapters.RecipesAdapter

class FavouritesFragment : Fragment() {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    val favouritesList : List<Recipe> = listOf(
        Recipe(1,"Omelette con ensalada de cherrys y hongos", "https://viapais.com.ar/resizer/mUQiFA14EV_X7bln_vY2CaTJ6V4=/982x551/smart/cloudfront-us-east-1.images.arcpublishing.com/grupoclarin/GIYWKYLGGVRTQNBYHA3TCOBXGU.jpg", "pipo89"),
        Recipe(2,"Espinacas a la crema", "https://dam.cocinafacil.com.mx/wp-content/uploads/2019/03/espinacas-a-la-crema.png", "maria_abc"),
        Recipe(3,"Pancito con queso super crocante", "https://i0.wp.com/blog.marianlaquecocina.com/wp-content/uploads/2018/04/20180416_144118.jpg", "deLaNonna")
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

        val viewManager = LinearLayoutManager(this.context)
        val viewAdapter = RecipesAdapter(favouritesList)

        binding.favoritesRV.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}