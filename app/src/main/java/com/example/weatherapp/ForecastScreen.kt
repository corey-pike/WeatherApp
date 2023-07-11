package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.text.SimpleDateFormat

@RequiresApi(Build.VERSION_CODES.O)
var localDate = LocalDate.now()!!

@RequiresApi(Build.VERSION_CODES.O)
var milliseconds = localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastScreen(navController: NavHostController) {
    val forecastItems = remember {
        listOf(
            DayForecast(
                date = 1L,
                sunrise = 9378234,
                sunset = 771872348,
                temp = ForecastTemp(20F, 15F, 25F),
                pressure = 1023.1F,
                humidity = 70
            ),
            DayForecast(
                date = 2L,
                sunrise = 81713902,
                sunset = 74282920,
                temp = ForecastTemp(22F, 18F, 26F),
                pressure = 1001F,
                humidity = 65
            ),
            DayForecast(
                date = 3L,
                sunrise = 812891984,
                sunset = 9891892398,
                temp = ForecastTemp(25F, 20F, 28F),
                pressure = 1002F,
                humidity = 60
            ),
            DayForecast(
                date = 4L,
                sunrise = 13923892,
                sunset = 8238289,
                temp = ForecastTemp(28F, 25F, 30F),
                pressure = 1003F,
                humidity = 55
            ),
            DayForecast(
                date = 5L,
                sunrise = 9100919034,
                sunset = 91389489,
                temp = ForecastTemp(30F, 28F, 32F),
                pressure = 1004F,
                humidity = 50
            ),
            DayForecast(
                date = 6L,
                sunrise = 8717890,
                sunset = 82382010,
                temp = ForecastTemp(28F, 25F, 30F),
                pressure = 1003F,
                humidity = 55
            ),
            DayForecast(
                date = 7L,
                sunrise = 84903020,
                sunset = 6234898,
                temp = ForecastTemp(28F, 25F, 30F),
                pressure = 1003F,
                humidity = 55
            ),
            DayForecast(
                date = 8L,
                sunrise = 187623498,
                sunset = 283479987,
                temp = ForecastTemp(28F, 25F, 30F),
                pressure = 1003F,
                humidity = 55
            ),
            DayForecast(
                date = 9L,
                sunrise = 8792932974,
                sunset = 23428982,
                temp = ForecastTemp(28F, 25F, 30F),
                pressure = 1003F,
                humidity = 55
            ),
            DayForecast(
                date = 10L,
                sunrise = 8932872387,
                sunset = 7282893,
                temp = ForecastTemp(28F, 25F, 30F),
                pressure = 1003F,
                humidity = 55
            ),
            DayForecast(
                date = 11L,
                sunrise = 63628728,
                sunset = 37273892,
                temp = ForecastTemp(28F, 25F, 30F),
                pressure = 1003F,
                humidity = 55
            ),
            DayForecast(
                date = 12L,
                sunrise = 72387462,
                sunset = 37378239,
                temp = ForecastTemp(28F, 25F, 30F),
                pressure = 1003F,
                humidity = 55
            ),
            DayForecast(
                date = 13L,
                sunrise = 3823982,
                sunset = 12902109,
                temp = ForecastTemp(28F, 25F, 30F),
                pressure = 1003F,
                humidity = 55
            ),
            DayForecast(
                date = 14L,
                sunrise = 48923498,
                sunset = 7843923,
                temp = ForecastTemp(28F, 25F, 30F),
                pressure = 1003F,
                humidity = 55
            ),
            DayForecast(
                date = 15L,
                sunrise = 9827389,
                sunset = 3272394,
                temp = ForecastTemp(28F, 25F, 30F),
                pressure = 1003F,
                humidity = 55
            ),
            DayForecast(
                date = 16L,
                sunrise = 23489490,
                sunset = 54389539,
                temp = ForecastTemp(28F, 25F, 30F),
                pressure = 1003F,
                humidity = 55
            )
        )
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
                itemsIndexed(forecastItems) { _, forecastItem ->
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
fun ForecastItemView(forecastItem: DayForecast) {

    val sunriseTime = Date(forecastItem.sunrise)
    val sunsetTime = Date(forecastItem.sunset)
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
                text = "July ${forecastItem.date}",
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ForecastPreview() {
    ForecastItemView(
        DayForecast(
            date = 1L,
            sunrise = 6017194752394,
            sunset = 1901L,
            temp = ForecastTemp(20F, 15F, 25F),
            pressure = 1023.1F,
            humidity = 70
        )
    )
}