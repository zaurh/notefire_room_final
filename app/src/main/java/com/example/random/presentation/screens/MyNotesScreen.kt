package com.example.random.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.random.R
import com.example.random.presentation.SharedViewModel
import com.example.random.presentation.components.MySearchBar
import com.example.random.presentation.components.NoteListItem
import com.google.gson.Gson

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val noteData = sharedViewModel.noteData.observeAsState(listOf())
    val sortedNoteData = noteData.value.sortedByDescending { it.noteId }

    val focus = LocalFocusManager.current


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "My Notes", color = Color.White) },
                backgroundColor = colorResource(id = R.color.blue),
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.gray))
                    .padding(5.dp)
            ) {


                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MySearchBar(
                        modifier = Modifier.padding(5.dp),
                        hint = "Search...",
                        onSearch = {
                            sharedViewModel.searchNote(it)
                        }
                    )
                    LazyVerticalGrid(
                        modifier = Modifier.weight(1f),
                        columns = GridCells.Fixed(2),
                        content = {
                            items(sortedNoteData) { note ->
                                NoteListItem(
                                    noteData = note,
                                    onClick = {
                                        val noteJson = Gson().toJson(note)
                                        navController.navigate("edit_note/$noteJson")
                                        focus.clearFocus()
                                    })
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
                        .size(70.dp), backgroundColor = colorResource(id = R.color.blue)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    )


}