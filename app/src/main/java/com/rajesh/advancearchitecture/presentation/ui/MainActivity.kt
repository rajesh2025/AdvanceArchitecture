package com.rajesh.advancearchitecture.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.rajesh.advancearchitecture.presentation.viewmodel.WeatherData
import com.rajesh.advancearchitecture.presentation.viewmodel.WeatherViewModel
import com.rajesh.advancearchitecture.ui.theme.AdvanceArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private var sumService : ISumService? = null
// private val connection = object :
// ServiceConnection{
//     override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
//         TODO("Not yet implemented")
//     }
//
//     override fun onServiceDisconnected(p0: ComponentName?) {
//         TODO("Not yet implemented")
//     }
// }

    private lateinit var weatherViewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdvanceArchitectureTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MobileDeviceUI(innerPadding)


                }
            }
        }

        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class]
        weatherViewModel.weatherData.observe(this) { weatherData ->
            UpdateUi(weatherData)
        }

        weatherViewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        weatherViewModel.fetchWeather()
    }
}

fun UpdateUi(weatherData: WeatherData) {

}

@Composable
fun MobileDeviceUI(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    MobileDeviceListScreen(
        modifier = Modifier.padding(innerPadding),
        navyController = navController
    )
}