package com.rajesh.practicekotlininandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rajesh.practicekotlininandroid.ui.theme.AdvanceArchitectureTheme

class MainActivity : ComponentActivity() {
    private val viewModel: StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvanceArchitectureTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->

                    StudentInfoCard(viewModel, contentPadding)
                }
            }
        }
    }
}


@Composable
fun StudentInfoCard(
    viewModel: StudentViewModel,
    contentPadding: PaddingValues
) {
    val studentState = viewModel.studentState.collectAsState()
    val studentData = studentState.value
    LaunchedEffect(Unit) {
        viewModel.fetchStudentData()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        when {
            studentData == null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(progress = { 0.5f })
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Loading student data...", fontSize = 16.sp)
                }
            }

            studentData.name == "Unknown" && studentData.address == "Address not available" -> {
                //Fallback
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(Icons.Default.Warning, contentDescription = "Error", tint = Color.Red)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Unable to load student details.",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Please check your connection and try again",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            else -> {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    onClick = { }) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Name: ${studentData.name ?: "N/A"}", fontWeight = FontWeight.Bold)
                        Text("Marks: ${studentData.marks ?: 0}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "🎁 Gift: ${studentData.gift ?: "No gift available"}",
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("📍 Address: ${studentData.address ?: "No address available"}")

                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AdvanceArchitectureTheme {
        Greeting("Android")
    }
}