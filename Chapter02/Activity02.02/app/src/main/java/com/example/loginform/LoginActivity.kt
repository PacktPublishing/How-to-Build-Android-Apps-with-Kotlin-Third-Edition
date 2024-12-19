package com.example.loginform

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = intent.getStringExtra(USERNAME_KEY)
        val password = intent.getStringExtra(PASSWORD_KEY)

        val loginResult = (username == "username" && password == "password")

        val resultIntent = Intent().apply {
            putExtra(LOGIN_RESULT, loginResult)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}