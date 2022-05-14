package com.example.quecomohoy.ui.diets

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.quecomohoy.R
import com.example.quecomohoy.databinding.FragmentDietListBinding
import com.example.quecomohoy.databinding.FragmentProfileBinding
import com.example.quecomohoy.ui.diets.interfaces.OnClickDietListener

/**
 * A fragment representing a list of Items.
 */
class DietsFragment : Fragment(), OnClickDietListener {

    private var _binding: FragmentDietListBinding? = null
    private val binding get() = _binding!!

    private lateinit var dietModel: DietViewModel
    private lateinit var dietAdapter: RecyclerView.Adapter<DietsAdapter.ViewHolder>;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDietListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dietModel = DietViewModel()
        dietAdapter = DietsAdapter(dietModel.findDiets(), this)

        with(binding.dietRecycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = dietAdapter
        }

        binding.saveDietButton.setOnClickListener{
            val name = dietModel.getCurrentDiet()?.name
            Toast.makeText(context, name, Toast.LENGTH_SHORT)
                .show()
        }
    }


    override fun onClick(dietIndex: Int) {
        val prevIndex = dietModel.currentDietIndex
        dietModel.selectNewDiet(dietIndex)
        dietAdapter.notifyItemChanged(dietIndex)
        dietAdapter.notifyItemChanged(prevIndex)
    }
}