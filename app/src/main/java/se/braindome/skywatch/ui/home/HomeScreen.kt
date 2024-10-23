package se.braindome.skywatch.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.braindome.skywatch.MainActivity
import se.braindome.skywatch.location.LocationRepository
import se.braindome.skywatch.ui.home.cards.MainInfoCard
import se.braindome.skywatch.ui.home.daily.DailyRow
import se.braindome.skywatch.ui.home.hourly.HourlyColumn


@SuppressLint("NewApi")
@Composable
fun HomeScreen(
    onClick: () -> Unit,
    viewModel: HomeViewModel
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.updateLocation(activity = context as MainActivity)
    }

    LoadingScreen(visible = uiState.value.loading)

    if (!uiState.value.loading) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { MainInfoCard(uiState) }
            item { HourlyColumn(uiState) }
            item { DailyRow(uiState) }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        {}, HomeViewModel(
        LocationRepository(
            context = LocalContext.current,
            //fusedLocationClient = TODO()
        ),
    ))
}
