package com.zaurh.notefirelocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zaurh.notefirelocal.data.local.NotesEntity
import com.zaurh.notefirelocal.data_store.StoreSettings
import com.zaurh.notefirelocal.presentation.NoteViewModel
import com.zaurh.notefirelocal.presentation.screens.AddEditNoteScreen
import com.zaurh.notefirelocal.presentation.screens.MainScreen
import com.zaurh.notefirelocal.ui.theme.NotefireLocalTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val dataStore = StoreSettings(context)
            var darkTheme by remember { mutableStateOf(false) }
            val savedDarkMode = dataStore.getDarkMode.collectAsState(initial = false)
            val scope = rememberCoroutineScope()

            NotefireLocalTheme(darkTheme = savedDarkMode.value ?: false) {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    MyAnimatedNavigation(
                        darkTheme = savedDarkMode.value ?: false
                    ){
                        scope.launch {
                            darkTheme = !(savedDarkMode.value ?: false)
                            dataStore.saveDarkMode(darkTheme)
                        }
                    }
                }
            }
        }
    }


}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MyAnimatedNavigation(
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit
) {
    val noteViewModel = viewModel<NoteViewModel>()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(
                navController = navController,
                noteViewModel = noteViewModel,
                darkTheme = darkTheme,
                onThemeUpdated = onThemeUpdated
            )
        }

        composable("add_note") {
            AddEditNoteScreen(navController = navController, noteViewModel = noteViewModel)
        }
        composable("edit_note"){
            val noteData =
                navController.previousBackStackEntry?.savedStateHandle?.get<NotesEntity>("noteData")
            noteData?.let {
                AddEditNoteScreen(
                    navController = navController,
                    noteData = noteData,
                    noteViewModel = noteViewModel
                )
            }
        }

    }
}