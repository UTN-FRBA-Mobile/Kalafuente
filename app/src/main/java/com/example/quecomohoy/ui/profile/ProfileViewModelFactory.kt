package com.example.quecomohoy.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quecomohoy.data.repositories.UserRepository

class ProfileViewModelFactory : ViewModelProvider.Factory  {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(UserRepository()) as T
    }
}
