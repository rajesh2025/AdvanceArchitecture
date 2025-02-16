package com.rajesh.advancearchitecture.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rajesh.advancearchitecture.domain.model.MobileDetail
import com.rajesh.advancearchitecture.presentation.viewmodel.MobileDeviceViewModel

@Composable
fun MobileDeviceListScreen(
    modifier: Modifier = Modifier,
    viewModel: MobileDeviceViewModel = hiltViewModel(),
    navyController: NavController
) {
    val state by viewModel.uiState
    when {
        state.isLoading -> CircularProgressIndicator()
        state.errorMessage != null -> Text("Error: ${state.errorMessage}")
        else ->
            LazyColumn(
                modifier
                    .fillMaxSize()
            ) {
                items(state.devices.size) { index ->
                    DeviceItem(state.devices[index]) {
//                        navyController.navigate("details/${state.devices[index].id}")
                    }
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = Color.Gray
                    )
                }
            }
    }
}

@Composable
fun DeviceItem(mobileDetail: MobileDetail, function: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
           ,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp, pressedElevation = 5.dp),
        onClick = {
            function.invoke()
        }
    ) {
        Text(modifier = Modifier
            .background(Color.Transparent)
            .wrapContentSize(),
            text = mobileDetail.name.takeIf { mobileDetail.id.isNotEmpty() } ?: "",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal)
    }

}