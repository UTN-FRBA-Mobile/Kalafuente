package com.example.quecomohoy.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quecomohoy.data.SignupException
import com.example.quecomohoy.data.model.user.User
import com.example.quecomohoy.data.repositories.RegistrationRepository
import com.example.quecomohoy.data.requests.UserSignupRequest
import com.example.quecomohoy.ui.Resource
import kotlinx.coroutines.launch

class RegistrationViewModel(private val registrationRepository: RegistrationRepository) : ViewModel()  {

    private val _user = MutableLiveData<Resource<User>>()
    val user : LiveData<Resource<User>> = _user

    fun signup(user : UserSignupRequest){
        _user.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val createdUser = registrationRepository.signup(user)
                _user.postValue(Resource.success(createdUser, null))
            } catch (e : SignupException){
                _user.postValue(Resource.error(e.message, null, null))
            } catch (e : Exception){
                _user.postValue(Resource.error("¡Ha ocurrido un error!", null, null))
            }
        }
    }

}