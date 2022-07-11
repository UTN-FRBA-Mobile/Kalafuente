package com.example.quecomohoy.ui.profile

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quecomohoy.data.model.perfil.UserPreferences
import com.example.quecomohoy.data.repositories.UserRepository
import com.example.quecomohoy.ui.Resource
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel()   {
    val profilePreferences = MutableLiveData<Resource<UserPreferences>>()
    fun getPreferencesByUserId(id : Int){
        viewModelScope.launch {
            try {
                val profilePreferences_ = userRepository.getPreferences(id)
                profilePreferences.postValue(Resource.success(profilePreferences_,null))
            }catch (e : Exception){
                Log.e(ContentValues.TAG, e.toString())
                profilePreferences.postValue(Resource.error("", null,null))
            }
        }
    }

     fun savePreferences(userId : Int, userPreference: UserPreferences) {
         viewModelScope.launch {
             try {
                 val userP = userRepository.savePreferences(userId, userPreference)
                 profilePreferences.postValue(Resource.success(userP,null))
             }catch (e : Exception){
                 Log.e(ContentValues.TAG, e.toString())
                 profilePreferences.postValue(Resource.error("", null,null))
             }
         }
    }
}