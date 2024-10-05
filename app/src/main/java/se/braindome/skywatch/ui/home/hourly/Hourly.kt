package se.braindome.skywatch.ui.home.hourly

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import se.braindome.skywatch.R
import se.braindome.skywatch.ui.home.HomeUiState
import se.braindome.skywatch.ui.home.HomeViewModel

@Composable
fun HourlyColumn(weatherState: State<HomeUiState>) {

}


@Composable
fun HourlyItem(weatherState: State<HomeUiState>) {

    Row(
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, Color.Black)
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
            Text(text = "12:00", style = MaterialTheme.typography.labelLarge)
            Text(text = "Mostly Clear")
        }
        Spacer(modifier = Modifier.width(64.dp))
        Column {
            Text(text = "8 C", style = MaterialTheme.typography.labelLarge)
            Text(text = "Feels like 6 C")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HourlyItemPreview() {
    val vm = HomeViewModel()
    val state = vm.uiState.collectAsState()
    HourlyItem(weatherState = state)
}