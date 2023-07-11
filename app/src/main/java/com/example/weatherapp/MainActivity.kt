package com.example.weatherapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
fun WeatherApp(navController: NavHostController) {
    val resources = LocalContext.current.resources
    val locationString = resources.getString(R.string.location)
    val currentTempString = resources.getString(R.string.currentTemp)
    val feelsLikeString = resources.getString(R.string.feelsLike)
    val highTempString = resources.getString(R.string.highTemp)
    val lowTempString = resources.getString(R.string.lowTemp)
    val humidityString = resources.getString(R.string.humidity)
    val pressureString = resources.getString(R.string.pressure)

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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = it.calculateTopPadding()),
        ) {
            Text(
                text = locationString,
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
                    text = currentTempString,
                    fontSize = 60.sp,
                )

                Image(
                    painter = painterResource(R.drawable.sunny),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .size(150.dp),
                )
            }

            Text(
                text = feelsLikeString,
                modifier = Modifier
                    .offset(y = (-40).dp)
                    .padding(start = 34.dp)
            )

            Text(
                text = lowTempString,
                modifier = Modifier.padding(start = 20.dp)
            )

            Text(
                text = highTempString,
                modifier = Modifier.padding(start = 20.dp)
            )

            Text(
                text = humidityString,
                modifier = Modifier.padding(start = 20.dp)
            )

            Text(
                text = pressureString,
                modifier = Modifier.padding(start = 20.dp)
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
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun WeatherAppPreview() {
    WeatherAppNavigation()
}
