package com.example.loginpage.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.firebase.firestore.auth.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    fun login(email: String, password: String) : User

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

}