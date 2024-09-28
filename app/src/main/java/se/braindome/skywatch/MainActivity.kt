package se.braindome.skywatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import se.braindome.skywatch.ui.home.HomeScreen
import se.braindome.skywatch.ui.theme.SkywatchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkywatchTheme {
                Scaffold { innerPadding ->
                    HomeScreen(innerPadding)
                }
            }
        }
    }
}

