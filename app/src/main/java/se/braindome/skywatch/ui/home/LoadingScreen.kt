package se.braindome.skywatch.ui.home

import android.R.attr.visible
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.braindome.skywatch.ui.theme.nightBackground
import java.lang.System.exit

@Composable
fun LoadingScreen(visible: Boolean) {
    AnimatedVisibility(
        visible = visible,
        exit = fadeOut(
            animationSpec = tween(150)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = nightBackground,
                strokeWidth = 3.dp,
                trackColor = nightBackground.copy(alpha = 0.5f),
            )
        }
    }
    }


@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    LoadingScreen(true)
}