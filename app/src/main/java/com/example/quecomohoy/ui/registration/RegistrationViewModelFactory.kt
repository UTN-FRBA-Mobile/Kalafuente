package com.example.quecomohoy.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quecomohoy.data.LoginDataSource
import com.example.quecomohoy.data.LoginRepository
import com.example.quecomohoy.data.repositories.RegistrationRepository
import com.example.quecomohoy.data.services.registration.RegistrationService
import com.example.quecomohoy.ui.login.LoginViewModel

class RegistrationViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(RegistrationRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}