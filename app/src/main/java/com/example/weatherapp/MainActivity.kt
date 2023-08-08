//MainActivity.kt
package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.weatherapp.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val currentConditionsViewModel: CurrentConditionsViewModel by viewModels()
    private val forecastViewModel: ForecastViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        WeatherAppNavigation(currentConditionsViewModel, forecastViewModel)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherAppNavigation(
    currentConditionsViewModel: CurrentConditionsViewModel,
    forecastViewModel: ForecastViewModel
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "weather") {
        composable("weather") { CurrentConditionsView(navController, currentConditionsViewModel) }
        composable("forecast") { ForecastScreen(navController, forecastViewModel) }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentConditionsView(
    navController: NavHostController,
    viewModel: CurrentConditionsViewModel
) {
    val currentWeather = viewModel.currentWeather.collectAsState(null)

    LaunchedEffect(Unit) {
        viewModel.fetchCurrentWeather("55125", "3723dded195b2c6ec85f31a5c5e0b1ae")
    }

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
        var zipCode by remember { mutableStateOf("") }
        Column {
            TextField(
                value = zipCode,
                onValueChange = { newZipCode ->
                    zipCode = newZipCode
                    viewModel.updateZipCode(newZipCode)
                },
                label = { Text("Enter ZIP Code") }
            )

            Button(
                onClick = {
                    val zipCodeResult = viewModel.zipCode.value
                    if (isValidZipCode(zipCodeResult)) {
                        viewModel.fetchCurrentWeather(
                            zipCodeResult,
                            "3723dded195b2c6ec85f31a5c5e0b1ae"
                        )
                    } else {
                        println("Invalid ZIP code: $zipCode")
                    }
                },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Search")
            }
        }
        currentWeather.value?.let { weather ->
            Column {
                Text(text = "Temperature: ${String.format("%.2f", weather.highTemp)}\u00B0")
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(it.calculateTopPadding())
                ) {
                    Text(
                        text = weather.name,
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
                            text = weather.temperature.temp.toString(),
                            fontSize = 60.sp,
                        )
                        WeatherConditionIcon(url = weather.iconUrl)
                    }
                    Text(
                        text = weather.temperature.feelsLike.toString(),
                        modifier = Modifier
                            .offset(y = (-40).dp)
                            .padding(start = 34.dp)
                    )
                    Button(
                        onClick = {
                            if (isValidZipCode(zipCode)) {
                                viewModel.fetchCurrentWeather(
                                    zipCode,
                                    "3723dded195b2c6ec85f31a5c5e0b1ae"
                                )
                                navController.navigate("forecast/$zipCode")
                            } else {
                                println("Invalid ZIP code: $zipCode")
                            }
                        },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Forecast")
                    }
                }
            }
        } ?: run {
            Text(text = "Loading...")
        }
    }
}

@Composable
fun WeatherConditionIcon(
    url: String
) {
    AsyncImage(model = url, contentDescription = "")
}