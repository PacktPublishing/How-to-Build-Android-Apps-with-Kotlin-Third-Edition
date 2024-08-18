package com.example.intentsintroduction

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intentsintroduction.ui.theme.IntentsIntroductionTheme

const val FULL_NAME_KEY = "FULL_NAME_KEY"

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntentsIntroductionTheme {
                MainScreen()
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun MainScreen() {

        var fullName by remember { mutableStateOf("") }
        val context = LocalContext.current
        val welcomeIntent = Intent(this, WelcomeActivity::class.java)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) }
                )
            }, modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = {
                        Text(
                            fontSize = 18.sp,
                            text = stringResource(id = R.string.full_name_label)
                        )
                    },
                    textStyle = TextStyle(fontSize = 20.sp), // Set custom font size here
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                )
                Button(
                    onClick = ({
                        if (fullName.isNotEmpty()) {
                            welcomeIntent.putExtra(FULL_NAME_KEY, fullName)
                            startActivity(welcomeIntent)
                        } else {
                            Toast.makeText(
                                context, getString(R.string.full_name_label),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }),
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.submit_button_text))
                }
            }
        }
    }
}
