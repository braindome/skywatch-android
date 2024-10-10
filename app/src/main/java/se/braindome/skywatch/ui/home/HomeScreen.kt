package se.braindome.skywatch.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import se.braindome.skywatch.ui.home.hourly.HourlyColumn
import se.braindome.skywatch.ui.home.search.SearchBar


@SuppressLint("NewApi")
@Composable
fun HomeScreen(
    padding: PaddingValues,
    onClick: () -> Unit,
    viewModel: HomeViewModel
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.updateLocation(activity = context as MainActivity)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar()
        MainInfoCard(uiState)
        HourlyColumn(uiState)
        Button(onClick = onClick) {
            Text(text = "Log Weather")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(PaddingValues(1.dp), {}, HomeViewModel(
        LocationRepository(
            context = TODO(),
            //fusedLocationClient = TODO()
        ),
    ))
}
