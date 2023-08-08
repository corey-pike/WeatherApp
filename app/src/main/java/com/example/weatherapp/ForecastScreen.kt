//ForecastScreen.kt
@file:Suppress("DEPRECATION")

package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.time.format.DateTimeFormatter
import java.util.Date
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastScreen(
    navController: NavHostController,
    viewModel: ForecastViewModel,
) {
    val forecastItems = viewModel.forecast.collectAsState(null)

    val zipCode = remember {
        val backStackEntry = navController.currentBackStackEntry
        backStackEntry?.arguments?.getString("zipCode") ?: ""
    }

    LaunchedEffect(Unit) {
        if (isValidZipCode(zipCode)) {
            viewModel.fetchForecast(zipCode, "3723dded195b2c6ec85f31a5c5e0b1ae")
        } else {
            println("Invalid ZIP code: $zipCode")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(),
                title = {
                    Text(
                        text = "Forecast",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp)
                            .offset(x = (-8).dp)
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Gray)
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 70.dp)
                    .offset(y = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(listOf(forecastItems)) { _, forecastItem ->
                    ForecastItemView(forecastItem = forecastItem)
                }
            }
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 32.dp),
                onClick = { navController.navigate("weather") }
            ) {
                Text(text = "Back to Main")
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastItemView(forecastItem: State<Forecast?>) {

    forecastItem.value?.let { forecastData ->

        val sunriseTime = Date(forecastData.sunrise)
        val sunsetTime = Date(forecastData.sunset)
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        val sunriseTimeFormatted =
            sunriseTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
                .format(formatter)
        val sunsetTimeFormatted =
            sunsetTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()

        Text("Forecast:")
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            forecastData.forecasts.forEach { forecastItem ->
                WeatherConditionIcon(url = forecastItem.iconUrl)

                Column(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(R.drawable.sunny),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(
                            text = forecastItem.date,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Right
                        )
                        Column(
                            modifier = Modifier.padding(start = 14.dp)
                        ) {
                            Text(
                                text = "Temp: ${forecastData.temperature.temp.toInt()}°",
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "High: ${forecastData.temperature.high.toInt()}°",
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                                Text(
                                    text = "Low: ${forecastData.temperature.low.toInt()}°",
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(end = 10.dp)
                                )
                            }
                        }
                        Column(
                            modifier = Modifier.padding(start = 8.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "Sunrise: ${sunriseTimeFormatted.format(formatter)}",
                                fontSize = 14.sp,
                                textAlign = TextAlign.Right
                            )
                            Text(
                                text = "Sunset: ${sunsetTimeFormatted.format(formatter)}",
                                fontSize = 14.sp,
                                textAlign = TextAlign.Right
                            )
                        }
                    }
                }
            }
        }
    }
}

fun isValidZipCode(zipCode: String): Boolean {
    return zipCode.matches(Regex("^\\d{5}$"))
}