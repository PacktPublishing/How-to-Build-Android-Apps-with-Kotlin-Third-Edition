package com.example.launchmodes

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.launchmodes.ui.theme.LaunchModesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchModesTheme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        val context = LocalContext.current

        Column(Modifier.padding(innerPadding)) {
            Button(
                onClick = {
                    context.startActivity(Intent(context, StandardActivity::class.java))
                },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    stringResource(id = R.string.standard_launch_mode),
                    fontSize = 28.sp
                )
            }
            Button(
                onClick = {
                    context.startActivity(Intent(context, SingleTopActivity::class.java))
                },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    stringResource(id = R.string.single_top_launch_mode),
                    fontSize = 28.sp
                )
            }
        }
    }

}

@Composable
@Preview
private fun MainScreenPreview() {
    MainScreen()
}