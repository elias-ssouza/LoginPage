package com.example.loginpage.viewmodel

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.loginpage.model.User
import com.example.loginpage.repository.UsersRepository


class UserViewModel (application: Application) : AndroidViewModel(application) {

    private val usersRepository = UsersRepository(getApplication())

    fun createUser(user: User) = usersRepository.createUser(user)


    fun login(email: String, password: String) : LiveData<User> = usersRepository.login(email, password)

    fun logout() = PreferenceManager.getDefaultSharedPreferences(getApplication()).let {
        it.edit().remove(USER_ID).apply()
    }

    fun resetPassword(email: String) = usersRepository.resetPassword(email)

    companion object {
        const val USER_ID = "USER_ID"
    }
}