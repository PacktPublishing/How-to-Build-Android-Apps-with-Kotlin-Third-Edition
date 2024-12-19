package com.example.loginform

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.loginform.ui.theme.LoginFormTheme

const val USERNAME_KEY = "USERNAME_KEY"
const val PASSWORD_KEY = "PASSWORD_KEY"
const val LOGIN_RESULT = "LOGIN_RESULT"

class MainActivity : ComponentActivity() {

    private var username by mutableStateOf("")
    private var password by mutableStateOf("")
    private var message by mutableStateOf("")
    private var showForm by mutableStateOf(true)

    private val startForResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val loginResult = data?.getBooleanExtra(LOGIN_RESULT, false) ?: false
            if (loginResult) {
                message = "Welcome, $username!"
                showForm = false
            } else {
                message = "Login failed. Please try again."
                showForm = true
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginFormTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showForm) {
                        LoginForm(
                            modifier = Modifier.padding(innerPadding),
                            username = username,
                            password = password,
                            onUsernameChange = { username = it },
                            onPasswordChange = { password = it },
                            message = message,
                            onLoginClick = {
                                if (username.isNotEmpty() && password.isNotEmpty()) {
                                    val intent = Intent(this, LoginActivity::class.java).apply {
                                        putExtra(USERNAME_KEY, username)
                                        putExtra(PASSWORD_KEY, password)
                                    }
                                    startForResult.launch(intent)
                                } else {
                                    message = "Please fill in all fields."
                                }
                            }
                        )
                    } else {
                        Box(Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                            Text(text = message, modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoginForm(
    modifier: Modifier,
    username: String,
    password: String,
    message: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        TextField(
            value = username,
            onValueChange = onUsernameChange,
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = message, modifier = Modifier.padding(16.dp))
    }
}