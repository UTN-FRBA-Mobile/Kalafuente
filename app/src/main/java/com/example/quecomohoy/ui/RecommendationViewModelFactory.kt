package com.example.quecomohoy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quecomohoy.data.repositories.RecommendationRepository
class RecommendationViewModelFactory  : ViewModelProvider.Factory  {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RecommendationViewModel(RecommendationRepository()) as T
    }
}