package com.example.quecomohoy.ui;

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quecomohoy.data.model.Recommendation
import com.example.quecomohoy.data.repositories.RecommendationRepository;
import kotlinx.coroutines.launch

public class RecommendationViewModel(private val recommendationRepository:RecommendationRepository) : ViewModel()  {
    val recommendations = MutableLiveData<Resource<List<Recommendation>>>()
    fun getRecommendationsByUser(id : Int){
        val additionalData = mapOf("searchTerm" to id)
        recommendations.postValue(Resource.loading(additionalData))
        viewModelScope.launch {
            try {
                val recommendations_ = recommendationRepository.getRecommendationsByUser(id)
                recommendations.postValue(Resource.success(recommendations_, additionalData))
            }catch (e : Exception){
                recommendations.postValue(Resource.error("", null, additionalData))
            }
        }
    }
}
