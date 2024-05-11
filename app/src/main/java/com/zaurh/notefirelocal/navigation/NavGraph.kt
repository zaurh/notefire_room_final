package com.zaurh.notefirelocal.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zaurh.notefirelocal.presentation.NoteViewModel
import com.zaurh.notefirelocal.presentation.screens.AddEditNoteScreen
import com.zaurh.notefirelocal.presentation.screens.MainScreen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MyAnimatedNavigation(
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit
) {
    val noteViewModel = viewModel<NoteViewModel>()
    val navController = rememberNavController()
    SharedTransitionLayout {

        NavHost(
            navController = navController,
            startDestination = Screen.MyNotesScreen.route
        ) {
            composable(
                route = Screen.MyNotesScreen.route
            ) {
                MainScreen(
                    navController = navController,
                    noteViewModel = noteViewModel,
                    darkTheme = darkTheme,
                    onThemeUpdated = onThemeUpdated,
                    animatedVisibilityScope = this
                )
            }

            composable(
                Screen.AddNoteScreen.route
            ) {
                AddEditNoteScreen(
                    navController = navController,
                    noteViewModel = noteViewModel,
                    animatedVisibilityScope = this
                )
            }
            composable(
                route = Screen.EditNoteScreen.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    },
                    navArgument("title") {
                        type = NavType.StringType
                    },

                    navArgument("desc") {
                        type = NavType.StringType
                    }
                )
            ) {
                val id = it.arguments?.getInt("id") ?: 0
                val title = it.arguments?.getString("title") ?: ""
                val desc = it.arguments?.getString("desc") ?: ""

                AddEditNoteScreen(
                    noteId = id,
                    title = title,
                    desc = desc,
                    animatedVisibilityScope = this,
                    navController = navController,
                    noteViewModel = noteViewModel
                )

            }
        }
    }
}