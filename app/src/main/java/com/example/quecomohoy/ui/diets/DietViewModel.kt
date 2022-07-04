package com.example.quecomohoy.ui.diets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quecomohoy.R
import com.example.quecomohoy.data.model.diet.*
import com.example.quecomohoy.data.repositories.DietRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class DietViewModel(private val dietRepository: DietRepository) : ViewModel() {

    private val _radioButtonId: MutableLiveData<Int> = MutableLiveData()
    val radioButtonId: LiveData<Int> = _radioButtonId



    fun selectCurrentDiet(dietId : Int) {
       _radioButtonId.postValue(getRadioButtonId(dietId))
    }

    fun saveDiet(optionId: Int, userId: Int) {
        val dietId = getDietId(optionId)
        viewModelScope.launch {
            dietRepository.saveUserDiet(dietId, userId)
        }
    }

    private fun getDietId(optionId: Int): Int {
        return when (optionId) {
            R.id.omnivore -> OMNIVORE_ID
            R.id.vegan -> VEGAN_ID
            R.id.vegetarian -> VEGETARIAN
            R.id.pescetarian -> PESCETARIAN
            R.id.other -> OTHER
            else -> throw Exception("")
        }
    }

    private fun getRadioButtonId(optionId: Int): Int {
        return when (optionId) {
            OMNIVORE_ID -> R.id.omnivore
            VEGAN_ID -> R.id.vegan
            VEGETARIAN -> R.id.vegetarian
            PESCETARIAN -> R.id.pescetarian
            OTHER ->  R.id.other
            else -> throw Exception("")
        }
    }


}