package com.example.loginpage.repository

import android.app.Application
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONObject
import com.example.loginpage.model.User
import com.example.loginpage.viewmodel.UserViewModel

class UsersRepository (application: Application) {

    private val firestore = FirebaseFirestore.getInstance()

    private val queue = Volley.newRequestQueue(application)

    private val preference = PreferenceManager.getDefaultSharedPreferences(application)

    fun login(email: String, password: String) : LiveData<User> {

        val liveData = MutableLiveData<User>(null)

        val params = JSONObject().also {
            it.put("email", email)
            it.put("password", password)
            it.put("returnSecureToken", true)
        }

    val request = JsonObjectRequest(
        Request.Method.POST
        , BASE_URL + SIGNIN + KEY
        , params
        , Response.Listener { response ->
            val localId = response.getString("localId")
            val idToken = response.getString("idToken")

            firestore.collection("users")
                .document(localId).get().addOnSuccessListener {
                    val user = it.toObject(User::class.java)
                    user?.id = localId
                    user?.password = idToken

                    liveData.value = user

                    preference.edit().putString(UserViewModel.USER_ID, localId).apply()

                    firestore.collection("users")
                        .document(localId).set(user!!)
                }
        }
        , Response.ErrorListener { error ->
            Log.e(this.toString(), error.message ?: "Error")
        }
    )

    queue.add(request)

    return liveData
}

fun createUser(user: User) {

    val params = JSONObject().also {
        it.put("email", user.email)
        it.put("password", user.password)
        it.put("returnSecureToken", true)
    }

    val request = JsonObjectRequest(Request.Method.POST
        , BASE_URL + SIGNUP + KEY
        , params
        , Response.Listener { response ->
            user.id = response.getString("localId")
            user.password = response.getString("idToken")

            firestore.collection("users")
                .document(user.id).set(user).addOnSuccessListener {
                    Log.d(this.toString(), "Usuário ${user.email} cadastrado com sucesso.")
                }
        }
        , Response.ErrorListener { error ->
            Log.e(this.toString(), error.message ?: "Error")
        }
    )

    queue.add(request)
}




fun resetPassword(email: String) {

    val params = JSONObject().also {
        it.put("email", email)
        it.put("requestType", "PASSWORD_RESET")
    }

    val request = JsonObjectRequest(Request.Method.POST
        , BASE_URL + PASSWORD_RESET + KEY
        , params
        , Response.Listener { response ->
            Log.d(this.toString(), response.keys().toString())
        }
        , Response.ErrorListener { error ->
            Log.e(this.toString(), error.message ?: "Error")
        }
    )

    queue.add(request)

}

    companion object {

        const val BASE_URL = "https://identitytoolkit.googleapis.com/v1/"

        const val SIGNUP = "accounts:signUp"

        const val SIGNIN = "accounts:signInWithPassword"

        const val PASSWORD_RESET = "accounts:sendOobCode"

        const val KEY = "?key=AIzaSyChEP-hS7rI1CoJjupv9mf0PACfBiVvOdU"
    }
}
