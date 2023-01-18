package com.bigoloo.lettersfall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.bigoloo.lettersfall.ui.MainNavigation
import com.bigoloo.lettersfall.ui.theme.LettersFallTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LettersFallTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.padding(8.dp),
                    content = {
                        MainNavigation(
                            modifier = Modifier.padding(it),
                            navController = navController
                        )
                    })
            }
        }
    }
}