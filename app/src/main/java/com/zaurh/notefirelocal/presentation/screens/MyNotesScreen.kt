@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.zaurh.notefirelocal.presentation.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zaurh.notefirelocal.R
import com.zaurh.notefirelocal.common.TestTags.ACTION_BUTTON_MAIN_SCREEN
import com.zaurh.notefirelocal.common.TestTags.ADD_NOTE_FLOATING_BUTTON
import com.zaurh.notefirelocal.common.TestTags.DROPDOWN_MENU_MAIN_SCREEN
import com.zaurh.notefirelocal.common.TestTags.GRID_CELLS
import com.zaurh.notefirelocal.common.TestTags.TITLE_BAR_MAIN_SCREEN
import com.zaurh.notefirelocal.common.findActivity
import com.zaurh.notefirelocal.common.sendMail
import com.zaurh.notefirelocal.common.showFeedbackDialog
import com.zaurh.notefirelocal.data_store.StoreSettings
import com.zaurh.notefirelocal.presentation.NoteViewModel
import com.zaurh.notefirelocal.presentation.components.MySearchBar
import com.zaurh.notefirelocal.presentation.components.NoteListItem
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavController,
    noteViewModel: NoteViewModel,
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    val uriHandler = LocalUriHandler.current

    val noteData = noteViewModel.searchNotes(text).collectAsState(listOf())
    val sortedNoteData = noteData.value.sortedByDescending { it.noteId }
    val focusManager = LocalFocusManager.current
    val focus = LocalFocusManager.current
    var dropdownState by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context.findActivity()
    val dataStore = StoreSettings(context)
    val saveGridCells = dataStore.getGridCells.collectAsState(initial = 2)
    val scope = rememberCoroutineScope()



    BackHandler(enabled = text.isNotEmpty(), onBack = {
        text = ""
        focusManager.clearFocus()
    })

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.testTag(TITLE_BAR_MAIN_SCREEN),
                        text = "My Notes",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    MaterialTheme.colorScheme.background
                ),
                actions = {
                    IconButton(modifier = Modifier.testTag(
                        ACTION_BUTTON_MAIN_SCREEN
                    ), onClick = {
                        dropdownState = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                    DropdownMenu(
                        modifier = Modifier.testTag(DROPDOWN_MENU_MAIN_SCREEN),
                        expanded = dropdownState,
                        onDismissRequest = { dropdownState = false }) {
                        DropdownMenuItem(modifier = Modifier.testTag(GRID_CELLS), leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = ""
                            )
                        }, trailingIcon = { Text(text = "${saveGridCells.value}") }, text = {
                            Text(text = "Grid count")
                        }, onClick = {
                            when (saveGridCells.value) {
                                1 -> {
                                    scope.launch {
                                        dataStore.saveGridCells(2)
                                    }
                                }
                                2 -> {
                                    scope.launch {
                                        dataStore.saveGridCells(3)
                                    }
                                }
                                else -> {
                                    scope.launch {
                                        dataStore.saveGridCells(1)
                                    }
                                }
                            }
                        })
                        DropdownMenuItem(leadingIcon = {
                            Icon(
                                painter = painterResource(id =
                                if (darkTheme) R.drawable.sun else R.drawable.dark_mode),
                                contentDescription = ""
                            )
                        }, text = {
                            Text(text = if (darkTheme) "Light theme" else "Dark theme")
                        }, onClick = {
                            onThemeUpdated()
                        })
                        DropdownMenuItem(leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = ""
                            )
                        }, text = {
                            Text(text = "Rate the app")
                        }, onClick = {
                            showFeedbackDialog(context = context, activity = activity){
                                uriHandler.openUri("https://play.google.com/store/apps/details?id=com.zaurh.notefirelocal")
                            }
                        })
                        DropdownMenuItem(leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = ""
                            )
                        }, text = {
                            Text(text = "Contact")
                        }, onClick = {
                            context.sendMail(to = "zaurway@gmail.com", subject = "Notefire")
                        })
                    }
                }
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MySearchBar(
                        modifier = Modifier.padding(10.dp),
                        text = text,
                        onSearch = {
                            text = it
                        }
                    )
                    LazyVerticalGrid(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp, end = 8.dp),
                        columns = GridCells.Fixed(saveGridCells.value),
                        content = {
                            items(sortedNoteData) { note ->
                                NoteListItem(
                                    noteData = note,
                                    navController = navController
                                )
                            }
                        })
                }


                FloatingActionButton(
                    onClick = {
                        navController.navigate("add_note")
                        focus.clearFocus()
                    },
                    modifier = Modifier
                        .align(alignment = Alignment.BottomEnd)
                        .padding(20.dp)
                        .size(70.dp)
                        .testTag(ADD_NOTE_FLOATING_BUTTON),
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        })

}

