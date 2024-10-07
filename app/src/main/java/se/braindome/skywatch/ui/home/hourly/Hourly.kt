package se.braindome.skywatch.ui.home.hourly

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.braindome.skywatch.R
import se.braindome.skywatch.ui.home.HomeUiState
import se.braindome.skywatch.ui.home.HomeViewModel
import se.braindome.skywatch.ui.utils.DateTimeUtils

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourlyColumn(weatherState: State<HomeUiState>) {
    Column(
        modifier = Modifier.fillMaxWidth().padding()
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.baseline_expand_less_24),
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp).size(34.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Next 24 Hours",
                style = MaterialTheme.typography.titleLarge,
            )
        }
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(24) { index -> HourlyItem(weatherState, index) }
        }
    }


}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourlyItem(weatherState: State<HomeUiState>, index: Int) {
    val isExpanded by rememberSaveable { mutableStateOf(true) }
    val hourlyState = weatherState.value.forecastResponse?.hourly?.get(index)
    Row(
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.wi_cloud),
            contentDescription = null,
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.width(32.dp))
        Column {
            Text(
                text = DateTimeUtils.convertToLocalTime(
                    dt = hourlyState?.dt ?: 0,
                    format24 = true
                ),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = hourlyState?.weather?.get(0)?.description.toString()
            )
        }
        Spacer(modifier = Modifier.width(64.dp))
        Column {
            Text(text = hourlyState?.temp.toString() + " C", style = MaterialTheme.typography.labelLarge)
            Text(text = hourlyState?.feels_like.toString() + " C")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HourlyItemPreview() {
    val vm = HomeViewModel()
    val state = vm.uiState.collectAsState()
    HourlyItem(weatherState = state, index = 0)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HourlyColumnPreview() {
    val vm = HomeViewModel()
    val state = vm.uiState.collectAsState()
    HourlyColumn(weatherState = state)
}