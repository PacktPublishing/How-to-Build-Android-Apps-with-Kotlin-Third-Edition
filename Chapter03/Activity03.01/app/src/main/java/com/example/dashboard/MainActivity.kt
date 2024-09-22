package com.example.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dashboard.ui.theme.DashboardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashboardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BusinessDashboard(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessDashboard(modifier: Modifier) {
    var selectedItem by remember { mutableStateOf("None") }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Business Dashboard",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            DashboardTile("Total Sales", selectedItem) { selectedItem = "Total Sales" }
            DashboardTile("Active Users", selectedItem) { selectedItem = "Active Users" }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            DashboardTile("Conversion Rate", selectedItem) { selectedItem = "Conversion Rate" }
            DashboardTile("Revenue Growth", selectedItem) { selectedItem = "Revenue Growth" }
        }

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(34.dp)
                .height(150.dp) // Increased height for detailed view
        ) {
            Box(contentAlignment = Alignment.Center) {
                DetailedStatistics(selectedItem = selectedItem)
            }
        }
    }
}

@Composable
fun DetailedStatistics(selectedItem: String) {
    when (selectedItem) {
        "Total Sales" -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Total Sales Breakdown", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Online Sales: $8,000", fontSize = 16.sp)
                Text(text = "In-store Sales: $4,000", fontSize = 16.sp)
                Text(text = "Year-over-Year Growth: +10%", fontSize = 16.sp)
            }
        }

        "Active Users" -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Active Users Breakdown",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Daily Active Users: 1,500", fontSize = 16.sp)
                Text(text = "Monthly Active Users: 2,400", fontSize = 16.sp)
                Text(text = "User Retention: 75%", fontSize = 16.sp)
            }
        }

        "Conversion Rate" -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Conversion Rate Details",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Website Conversion: 4.8%", fontSize = 16.sp)
                Text(text = "App Conversion: 6.2%", fontSize = 16.sp)
                Text(text = "Overall Conversion: 5.4%", fontSize = 16.sp)
            }
        }

        "Revenue Growth" -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Revenue Growth Overview",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Last Month: +7.8%", fontSize = 16.sp)
                Text(text = "Last Quarter: +8.2%", fontSize = 16.sp)
                Text(text = "Year-to-Date: +15.5%", fontSize = 16.sp)
            }
        }

        else -> {
            Text(text = "Select an item to view details", fontSize = 16.sp)
        }
    }
}

@Composable
fun DashboardTile(title: String, selectedItem: String, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp,
        color = if (selectedItem == title) Color(0xFFBBDEFB) else Color.White,
        modifier = Modifier
            .size(150.dp)
            .clickable { onClick() }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    BusinessDashboard(modifier = Modifier.padding(20.dp))
}

