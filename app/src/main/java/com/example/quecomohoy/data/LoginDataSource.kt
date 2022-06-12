package com.example.quecomohoy.data

import com.example.quecomohoy.data.model.LoggedInUser
import com.example.quecomohoy.data.model.user.User
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(user: User): Result<LoggedInUser> {
        try {
            val userLogged = LoggedInUser(user.name, user.username, user.image, user.id)
            return Result.Success(userLogged)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }
    fun logout() {
        // TODO: revoke authentication
    }
}