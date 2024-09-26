package se.braindome.skywatch.ui.home.cards

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.braindome.skywatch.ui.theme.Typography

@Composable
fun MainInfoCard() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .border(width = 1.dp, color = Color.Black)
            .padding(16.dp),
        ) {
        Column {
            Row {
                Text(
                    text = "25C",
                    style = Typography.displayLarge,
                    modifier = Modifier.weight(0.5f)
                )
                Icon(
                    imageVector = Icons.Filled.WbSunny,
                    contentDescription = "Sunny",
                    modifier = Modifier.size(140.dp)
                )
            }
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainInfoCardPreview() {
    MainInfoCard()
}
