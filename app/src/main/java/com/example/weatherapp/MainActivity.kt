//MainActivity.kt
package com.example.weatherapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppNavigation()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "weather") {
        composable("weather") { WeatherApp(navController) }
        composable("forecast") { ForecastScreen(navController) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(
    navController: NavHostController,
    viewModel: CurrentConditionsViewModel = hiltViewModel()
) {

    val currentConditions by viewModel.currentConditionsLiveData.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(),
                title = {
                    Text(
                        text = "Weather App",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp)
                            .offset(x = (-8).dp)
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Gray)
            )
        }
    ) {
        currentConditions?.let { conditions ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it.calculateTopPadding())
            ) {
                Text(
                    text = conditions.name,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = conditions.main.temp.toString(),
                        fontSize = 60.sp,
                    )
                    conditions.weather.firstOrNull()?.let {
                        WeatherConditionIcon(url = it.icon)
                    }
                }
                Text(
                    text = conditions.main.feels_like.toString(),
                    modifier = Modifier
                        .offset(y = (-40).dp)
                        .padding(start = 34.dp)
                )
                Button(
                    onClick = { navController.navigate("forecast") },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Forecast")
                }
            }
        } ?: run {
            // Handle the case where currentConditions is null or weather list is empty
            Text(text = "Loading...")
        }
    }
}
    @Composable
    fun WeatherConditionIcon(url: String) {
        AsyncImage(model = url, contentDescription = "")
    }
