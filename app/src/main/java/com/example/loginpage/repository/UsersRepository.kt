package com.example.loginpage.repository

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.toolbox.Volley
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import org.json.JSONObject

class UsersRepository (application: Application) {

    private val firestore = FirebaseFirestore.getInstance()

    private val queue = Volley.newRequestQueue(application)

    private val preference = PreferenceManager.getDefaultSharedPreferences(application)

    fun login(email: String, password: String) {

        val liveData = MutableLiveData<User>(null)

        val params = JSONObject().also {
            it.put("email", email)
            it.put("password", password)
            it.put("returnSecureToken", true)
        }
    }

    companion object {

        const val BASE_URL = "https://identitytoolkit.googleapis.com/v1/"

        const val SIGNUP = "accounts:signUp"

        const val SIGNIN = "accounts:signInWithPassword"

        const val PASSWORD_RESET = "accounts:sendOobCode"

        const val KEY = "?key=AIzaSyChEP-hS7rI1CoJjupv9mf0PACfBiVvOdU"
    }
}