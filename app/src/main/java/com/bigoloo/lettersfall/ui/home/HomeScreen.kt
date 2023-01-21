package com.bigoloo.lettersfall.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bigoloo.lettersfall.R
import com.bigoloo.lettersfall.models.ChosenLanguage
import com.bigoloo.lettersfall.models.GameStatus
import com.bigoloo.lettersfall.ui.base.Actionable
import com.bigoloo.lettersfall.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier,
    navController: NavHostController
) {
    val wordFallViewModel: HomeViewModel = koinViewModel()
    val state = wordFallViewModel.viewState.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = com.bigoloo.lettersfall.R.string.welcome_to_game)
        )
        val currentGameStatus = state.value.asLoaded()
        LaunchedEffect(key1 = Unit, block = {
            wordFallViewModel.effect.collect {
                when (it) {
                    HomeEffect.NavigateToQuestionScreen -> navController.navigate("questionScreen")
                }
            }
        })
        var selectedLanguage by remember {
            mutableStateOf(ChosenLanguage.English)
        }
        LanguageRadioGroup(selectedOption = selectedLanguage) {
            selectedLanguage = it
        }
        if (currentGameStatus != null) {
            when (currentGameStatus.startGameActionable) {
                is Actionable.Failed -> {
                    Toast.makeText(
                        LocalContext.current,
                        stringResource(id = R.string.try_again),
                        Toast.LENGTH_LONG
                    ).show()
                }
                Actionable.InProgress -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(
                            48.dp
                        )
                    )
                }
                else -> {}
            }
            when (currentGameStatus.gameState) {
                GameStatus.NotStarted -> {
                    Button(onClick = {
                        wordFallViewModel.dispatch(HomeAction.InitiateGame(selectedLanguage))
                    }) {
                        Text(text = "New Game")
                    }
                }
                is GameStatus.Started -> {
                    Button(onClick = {
                        wordFallViewModel.dispatch(HomeAction.InitiateGame(selectedLanguage))
                    }) {
                        Text(text = "Continue")
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
fun LanguageRadioGroup(
    selectedOption: ChosenLanguage,
    onSelect: (chosenLanguage: ChosenLanguage) -> Unit
) {
    Column {
        ChosenLanguage.values().forEach { language ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (language == selectedOption),
                        onClick = {
                            onSelect(language)
                        }
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = CenterVertically
            ) {
                RadioButton(
                    selected = (language == selectedOption),
                    onClick = { onSelect(language) }
                )
                Text(
                    text = stringResource(
                        id = if (language == ChosenLanguage.English)
                            com.bigoloo.lettersfall.R.string.english
                        else com.bigoloo.lettersfall.R.string.spanish
                    ),
                    style = Typography.bodyMedium
                )
            }
        }
    }
}
