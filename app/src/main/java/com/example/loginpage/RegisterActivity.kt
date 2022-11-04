package com.example.loginpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import com.example.loginactivity.R
import com.example.loginpage.model.User
import com.example.loginpage.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText


class RegisterActivity : AppCompatActivity() {

    lateinit var registerName: TextInputEditText
    lateinit var registerEmail: TextInputEditText
    lateinit var registerPassword: TextInputEditText
    lateinit var btnRegister: Button

    private val userViewModel by viewModels<UserViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    registerName = findViewById(R.id.register_username)
    registerEmail = findViewById(R.id.txt_edit_email)
    registerPassword = findViewById(R.id.txt_edit_password)

    btnRegister = findViewById(R.id.btn)
    btnRegister.setOnClickListener {
        if(validate()) {
            val user = User(
                name = registerName.text.toString(),
                email = registerEmail.text.toString(),
                password = registerPassword.text.toString(),
            )

            userViewModel.createUser(user)
            userViewModel.login(user.email, user.password).observe(this, Observer {
                finish()
            })
        }
    }
}

private fun validate() : Boolean {
    var isValid = true

    registerName.apply {
        if(text.isNullOrEmpty()) {
            error = "Preencha o campo nome."
            isValid = false
        } else {
            error = null
        }
    }
    registerEmail.apply {
        if(text.isNullOrEmpty()) {
            error = "Preencha o campo email."
            isValid = false
        } else {
            error = null
        }
    }
    registerPassword.apply {
        if(text.isNullOrEmpty()) {
            error = "Preencha o campo a senha."
            isValid = false
        } else {
            error = null
        }
    }

    return isValid
}

