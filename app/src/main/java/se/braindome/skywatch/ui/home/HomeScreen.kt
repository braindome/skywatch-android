package se.braindome.skywatch.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import se.braindome.skywatch.ui.home.cards.MainInfoCard
import se.braindome.skywatch.ui.home.search.SearchBar


@Composable
fun HomeScreen(
    padding: PaddingValues,
    onClick: () -> Unit,
    viewModel: HomeViewModel
) {
    val uiState = viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize().padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar()
        MainInfoCard(
            currentTemp = uiState.value.forecastResponse?.current?.temp
                .toString(),
            currentWeather = uiState.value.forecastResponse?.current?.weather?.get(0)?.description
                .toString(),
            weatherIconUrl = uiState.value.forecastResponse?.current?.weather?.get(0)?.icon
                .toString()
        )
        Button(onClick = onClick) {
            Text(text = "Log Weather")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(PaddingValues(8.dp), {}, HomeViewModel())
}
