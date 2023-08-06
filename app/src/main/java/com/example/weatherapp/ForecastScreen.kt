//ForecastScreen.kt
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
    viewModel: ForecastViewModel = hiltViewModel()
) {
    val forecastItems by viewModel.forecastLiveData.observeAsState()

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
fun ForecastItemView(forecastItem: WeatherData?) {

    val sunriseTime = Date(forecastItem!!.sys.sunrise)
    val sunsetTime = Date(forecastItem.sys.sunset)
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    val sunriseTimeFormatted = sunriseTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
        .format(formatter)
    val sunsetTimeFormatted = sunsetTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()

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
                text = "${forecastItem.dt}",
                fontSize = 16.sp,
                textAlign = TextAlign.Right
            )
            Column(
                modifier = Modifier.padding(start = 14.dp)
            ) {
                Text(
                    text = "Temp: ${forecastItem.temp.day.toInt()}°",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "High: ${forecastItem.temp.max.toInt()}°",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Text(
                        text = "Low: ${forecastItem.temp.min.toInt()}°",
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
