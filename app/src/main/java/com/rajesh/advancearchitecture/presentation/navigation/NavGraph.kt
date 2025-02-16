package com.rajesh.advancearchitecture.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rajesh.advancearchitecture.presentation.ui.DeviceDetailScreen
import com.rajesh.advancearchitecture.presentation.ui.MobileDeviceListScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "device_list") {
        composable("device_list") {
            MobileDeviceListScreen(navyController = navController)
        }
        composable("details/{id}") { navBackStackEntry ->
            val deviceId = navBackStackEntry.arguments?.getString("id")
            DeviceDetailScreen(deviceId = deviceId)
        }
    }

}