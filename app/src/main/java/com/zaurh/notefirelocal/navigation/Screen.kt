package com.zaurh.notefirelocal.navigation

sealed class Screen(val route: String){
    data object MyNotesScreen: Screen(route = "notes_screen")
    data object AddNoteScreen: Screen(route = "add_note")
    data object EditNoteScreen: Screen(route = "edit_note/{id}/{title}/{desc}")
}