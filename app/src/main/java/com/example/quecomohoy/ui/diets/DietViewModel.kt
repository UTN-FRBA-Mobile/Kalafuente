package com.example.quecomohoy.ui.diets

import androidx.lifecycle.ViewModel
import com.example.quecomohoy.data.model.diet.Diet
import com.example.quecomohoy.ui.diets.placeholder.PlaceholderContent

class DietViewModel : ViewModel() {

    val diets: MutableList<Diet> = ArrayList()
    var currentDietIndex : Int = -1

    fun findDiets(): MutableList<Diet> {
        if(diets.isEmpty()){
            diets.addAll(PlaceholderContent.ITEMS)
        }
        return diets;
    }

    fun selectNewDiet(dietIndex : Int){
        if(currentDietIndex > -1){
            diets[currentDietIndex].selected = false
        }
        currentDietIndex = dietIndex
        val newDiet = diets[dietIndex]
        newDiet.selected = true
    }

    fun getCurrentDiet(): Diet? {
        return if(currentDietIndex >= 0) diets[currentDietIndex] else null
    }

}