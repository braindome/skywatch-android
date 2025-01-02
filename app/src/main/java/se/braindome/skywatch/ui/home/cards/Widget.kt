package se.braindome.skywatch.ui.home.cards

import android.R.style.Widget
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import se.braindome.skywatch.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import se.braindome.skywatch.ui.home.HomeUiState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun WidgetGrid(weatherState: State<HomeUiState>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .height(800.dp)
            .padding(horizontal = 16.dp)
            .background(Color.Black)
            .fillMaxWidth()
    ) {
        items(2) {
            Widget(weatherState)
        }
    }
}


@Composable
fun Widget(weatherState: State<HomeUiState>) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.background(Color.Blue, RoundedCornerShape(10.dp)).padding(8.dp)
        ) {
            Text(text = "Title")
            Row {
                Column {
                    Text("Data row 1")
                    Text("Data row 2")
                }
                Icon(
                    painter = painterResource(id = R.drawable.wi_alien),
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Widget(weatherState = remember { mutableStateOf(HomeUiState(forecastResponse = null, loading = false))})
}

@Preview(showBackground = true)
@Composable
fun WidgetGridPreview() {
    WidgetGrid(weatherState = remember { mutableStateOf(HomeUiState(forecastResponse = null, loading = false))})
}