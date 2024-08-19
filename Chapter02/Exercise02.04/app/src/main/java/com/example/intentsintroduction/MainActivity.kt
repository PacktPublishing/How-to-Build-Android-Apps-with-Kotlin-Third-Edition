package com.example.intentsintroduction

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intentsintroduction.MainActivity.Companion.FULL_NAME_KEY
import com.example.intentsintroduction.ui.theme.IntentsIntroductionTheme


class MainActivity : ComponentActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntentsIntroductionTheme {
                MainScreen()
            }
        }
    }

    companion object {
        const val FULL_NAME_KEY = "FULL_NAME_KEY"
    }
}

@Composable
private fun MainScreen() {
    var fullName by remember { mutableStateOf("") }
    val context = LocalContext.current
    val welcomeIntent =
        Intent(context, WelcomeActivity::class.java)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
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
                    .fillMaxWidth()
            )
            Button(
                onClick = ({
                    if (fullName.isNotEmpty()) {
                        welcomeIntent.putExtra(FULL_NAME_KEY, fullName)
                        context.startActivity(welcomeIntent)
                    } else {
                        Toast.makeText(
                            context, context.getString(R.string.full_name_label),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }),

                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.submit_button_text))
            }
        }
    }


}

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen()
}

