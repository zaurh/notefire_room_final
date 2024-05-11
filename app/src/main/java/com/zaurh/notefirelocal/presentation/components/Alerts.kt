package com.zaurh.notefirelocal.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DiscardChangesAlert(
    onDismiss: () -> Unit,
    onDiscard: () -> Unit
) {
    AlertDialog(title = { Text(text = "Discard changes?") }, text = {
        Text(text = "Are you sure you want to discard all changes?")
    }, onDismissRequest = { onDismiss() }, confirmButton = {
        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),onClick = {
            onDiscard()
        }) {
            Text(text = "Discard", color = MaterialTheme.colorScheme.error)
        }
    }, dismissButton = {
        Button(colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background
        ),onClick = { onDismiss() }) {
            Text(text = "Cancel", color = Color.White)
        }
    })
}

@Composable
fun DeleteNoteAlert(
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(title = { Text(text = "Delete note?") }, text = {
        Text(text = "Are you sure you want to delete note?")
    }, onDismissRequest = { onDismiss() }, confirmButton = {
        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),onClick = {
            onDelete()
        }) {
            Text(text = "Delete", color = MaterialTheme.colorScheme.error)
        }
    }, dismissButton = {
        Button(colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background
        ),onClick = { onDismiss() }) {
            Text(text = "Cancel", color = Color.White)
        }
    })
}