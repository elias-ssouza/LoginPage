package com.example.loginpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.loginactivity.R
import com.example.loginpage.viewmodel.UserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    lateinit var btnRegister: Button
    lateinit var btnUserLogin: Button
    lateinit var btnPasswordReset: TextView
    lateinit var loginEmail: TextInputEditText
    lateinit var loginPassword: TextInputEditText
    lateinit var dialogResetPassword: AlertDialog

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        loginEmail = findViewById(R.id.username)
        loginPassword = findViewById(R.id.password)

        buildResetPasswordDialog()

        btnUserLogin = findViewById(R.id.btn_login)
        btnUserLogin.setOnClickListener {
            userViewModel.login(loginEmail.text.toString(), loginPassword.text.toString()).observe(this, Observer {

                if(it == null)
                    Toast.makeText(applicationContext, getString(R.string.login_ok)), Toast.LENGTH_SHORT).show()
                else
                    finish()

            })
        }

        btnRegister = findViewById(R.id.btn_register)
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnPasswordReset = findViewById(R.id.tv_forgot_password)
        btnPasswordReset.setOnClickListener {
            dialogResetPassword.show()
        }
    }

    private fun buildResetPasswordDialog() {

        val view = LayoutInflater.from(this).inflate(R.layout.dialog_reset_password, null)
        val etEmail = view.findViewById<TextInputEditText>(R.id.txt_edit_login_email)

        dialogResetPassword = MaterialAlertDialogBuilder(this)
            .setPositiveButton("Resetar") { dialog, which ->
                userViewModel.resetPassword(etEmail.text.toString())
                Toast.makeText(this, "Verifique seu email para resetar a senha", Toast.LENGTH_LONG).show()
                etEmail.text?.clear()
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                etEmail.text?.clear()
            }
            .setIcon(android.R.drawable.ic_dialog_email)
            .setView(view)
            .setTitle("Preencha seu email para resetar sua senha.")
            .create()
    }
}