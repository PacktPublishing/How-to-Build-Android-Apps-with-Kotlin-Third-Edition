package com.example.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.settings.ui.theme.HeaderTextStyle
import com.example.settings.ui.theme.SettingsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SettingsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SettingsContainer(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SettingsContainer(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Header
        SettingsHeader()
        // Image
        SettingsImage()
        // Consent cookies
        SettingsCheckbox()
        // Stream over wifi
        SettingsSwitch()
        // App Brightness
        SettingsSlider()
        // Progress bars
        SettingsProgressBars()
        // Radio Buttons
        SettingsRadioButton()
        // Sign Out Button
        SignOutButton()
    }
}

@Composable
fun SettingsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = HeaderTextStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 16.dp, end = 12.dp),
        )
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = stringResource(id = R.string.settings_icon_description),
            modifier = Modifier.padding(end = 12.dp)
        )
    }
}

@Composable
fun SettingsImage() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.settings_profile_image),
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 18.sp,
        )
        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_camera),
            contentDescription = stringResource(id = R.string.settings_profile_image),
            modifier = Modifier.padding(end = 6.dp)
        )
    }
}

@Composable
fun SettingsCheckbox() {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.settings_consent),
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 18.sp,
        )
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.padding(start = 100.dp, end = 4.dp)
        )
    }
}

@Composable
fun SettingsSwitch() {
    var isSwitched by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.settings_mobile_streaming),
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 18.sp,
        )
        Switch(
            checked = isSwitched,
            onCheckedChange = { isSwitched = it },
            modifier = Modifier.padding(start = 100.dp, end = 12.dp)
        )
    }
}

@Composable
fun SettingsSlider() {
    var sliderValue by remember { mutableStateOf(0f) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.settings_brightness),
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 18.sp,
        )
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            modifier = Modifier.padding(start = 100.dp, end = 6.dp)
        )
    }
}

@Composable
fun SettingsProgressBars() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Spinner Progress Bar
        CircularProgressIndicator(
            modifier = Modifier.padding(16.dp)
        )
        // Linear Progress Bar
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun SignOutButton() {
    var showDialog by remember { mutableStateOf(false) }

    Button(onClick = { showDialog = true }) {
        Text(text = stringResource(id = R.string.show_alert_dialog))
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(id = R.string.alert_title)) },
            text = { Text(text = stringResource(id = R.string.alert_message)) },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun SettingsRadioButton() {
    var selectedColor by remember { mutableStateOf("Red") }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(text = "Choose a color:", modifier = Modifier.padding(bottom = 8.dp))
        listOf("Red", "Green", "Blue").forEach { color ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = (color == selectedColor),
                    onClick = { selectedColor = color },
                    colors = RadioButtonDefaults.colors()
                )
                Text(text = color, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Preview
@Composable
fun SettingsContainerPreview() {
    SettingsContainer()
}