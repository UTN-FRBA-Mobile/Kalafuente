package com.example.quecomohoy.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.quecomohoy.data.LoginRepository
import com.example.quecomohoy.data.Result

import com.example.quecomohoy.R

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _userInformation = MutableLiveData<LoggedInUserView>(
        LoggedInUserView(displayName = "")
    );
    val userInformation: LiveData<LoggedInUserView> = _userInformation

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)
        Log.d("Loggin view------", "entre---------------")
        if (result is Result.Success) {
            val loggedUserView = LoggedInUserView(displayName = result.data.displayName)
            _loginResult.value =
                LoginResult(success = loggedUserView)
            //Por ahora solo el nombre
           _userInformation.value = loggedUserView
            Log.d("Loggin view------ userInformation()",userInformation.value.toString())
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}