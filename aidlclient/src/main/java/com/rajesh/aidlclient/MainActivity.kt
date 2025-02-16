package com.rajesh.aidlclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rajesh.aidlclient.ui.theme.AdvanceArchitectureTheme

class MainActivity : ComponentActivity() {
private val viewModel: SumViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdvanceArchitectureTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        bindService(
            applicationContext,
            viewModel
            )
    }


    override fun onStop() {
        super.onStop()

//        if (viewModel.isBound) {
//            applicationContext.unbindService(viewModel.connection)
//            viewModel.isBound = false
//        }
    }
}


private fun bindService(
    context: Context,
    viewModel: SumViewModel
) {
    val intent = Intent()
    intent.setComponent(
        ComponentName("com.rajesh.advancearchitecture", "com.rajesh.advancearchitecture.SumService")
    )
    context.bindService(intent, viewModel.connection, Context.BIND_AUTO_CREATE)

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