package se.braindome.skywatch.ui.home.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    DockedSearchBar(
        query = "Search location",
        onQueryChange = {},
        onSearch = {},
        active = false,
        onActiveChange = {},
        colors = SearchBarDefaults.colors(
            containerColor = Color.LightGray.copy(alpha = 0.5f),
            dividerColor = Color.Transparent,
            inputFieldColors = TextFieldDefaults.colors(),
        ),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        shadowElevation = 0.2.dp,
        shape = RoundedCornerShape(10.dp),
        trailingIcon = { Icon(Icons.Default.Settings, contentDescription = null) },
        modifier = Modifier.fillMaxWidth().padding(8.dp)

    ) {
        Text(text = "Search")
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TopBarPreview() {
    SearchBar()

}