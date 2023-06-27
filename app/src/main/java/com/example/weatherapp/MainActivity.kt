package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.ui.theme.WeatherAppTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    WeatherApp()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp()
{
    val resources = LocalContext.current.resources
    val locationString = resources.getString(R.string.location)
    val currentTempString = resources.getString(R.string.currentTemp)
    val feelsLikeString = resources.getString(R.string.feelsLike)
    val highTempString = resources.getString(R.string.highTemp)
    val lowTempString = resources.getString(R.string.lowTemp)
    val humidityString = resources.getString(R.string.humidity)
    val pressureString = resources.getString(R.string.pressure)

    Column(
        verticalArrangement = Arrangement.Top
    ) {
        TopAppBar(
            title = { Text(
                text = "Weather App",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 14.dp)
                    .background(Color.Gray)) }
        )

        Text(
            text = locationString,
            modifier = Modifier
                .padding(bottom = 14.dp)
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
                    .size(100.dp),
            )
        }

        Text(
            text = feelsLikeString,
            modifier = Modifier.padding(start = 32.dp, bottom = 26.dp)
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
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherAppPreview() {
    WeatherAppTheme {
        WeatherApp()
    }
}
