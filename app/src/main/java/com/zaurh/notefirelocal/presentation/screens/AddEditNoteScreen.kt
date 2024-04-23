@file:OptIn(ExperimentalMaterial3Api::class)

package com.zaurh.notefirelocal.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.zaurh.notefirelocal.R
import com.zaurh.notefirelocal.common.TestTags.ADD_EDIT_TITLE_TAG
import com.zaurh.notefirelocal.data.local.NotesEntity
import com.zaurh.notefirelocal.presentation.NoteViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteData: NotesEntity? = null,
    noteViewModel: NoteViewModel
) {
    val focus = LocalFocusManager.current
    var titleTf by rememberSaveable { mutableStateOf(noteData?.noteTitle ?: "") }
    var descriptionTf by rememberSaveable { mutableStateOf(noteData?.noteDescription ?: "") }
    var pickedBackgroundColor by rememberSaveable {
        mutableIntStateOf(
            noteData?.backgroundColor ?: Color.White.toArgb()
        )
    }
    var pickedTitleColor by rememberSaveable {
        mutableIntStateOf(
            noteData?.title_color ?: Color.Black.toArgb()
        )
    }
    var pickedNoteColor by rememberSaveable {
        mutableIntStateOf(
            noteData?.note_color ?: Color.Black.toArgb()
        )
    }
    var pickedTitleFontSize by rememberSaveable {
        mutableFloatStateOf(
            noteData?.title_font_size ?: 20F
        )
    }
    var pickedNoteFontSize by rememberSaveable {
        mutableFloatStateOf(
            noteData?.note_font_size ?: 14F
        )
    }
    var titleIsItalic by rememberSaveable { mutableStateOf(noteData?.title_italic ?: false) }
    var noteIsItalic by rememberSaveable { mutableStateOf(noteData?.note_italic ?: false) }
    var titleIsBold by rememberSaveable { mutableStateOf(noteData?.title_bold ?: true) }
    var noteIsBold by rememberSaveable { mutableStateOf(noteData?.note_bold ?: false) }
    var titleIsUnderline by rememberSaveable { mutableStateOf(noteData?.title_underline ?: false) }
    var noteIsUnderline by rememberSaveable { mutableStateOf(noteData?.note_underline ?: false) }
    var titleIsLineThrough by rememberSaveable { mutableStateOf(noteData?.title_line_through ?: false) }
    var noteIsLineThrough by rememberSaveable { mutableStateOf(noteData?.note_line_through ?: false) }
    var fontColorPicker by rememberSaveable { mutableStateOf(false) }
    var backgroundColorPicker by rememberSaveable { mutableStateOf(false) }
    var fontSizeState by rememberSaveable { mutableStateOf(false) }
    val fontController = rememberColorPickerController()
    val backgroundController = rememberColorPickerController()

    var deleteAlert by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    if (noteData != null) {
                        IconButton(onClick = {
                            deleteAlert = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                    }
                },
                title = {
                    Text(
                        text = if (noteData != null) "Edit note" else "Add note",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceAround
                    ) {


                        Button(colors = ButtonDefaults.buttonColors(
                            containerColor = if (fontSizeState) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary
                        ), onClick = {
                            focus.clearFocus()
                            backgroundColorPicker = false
                            fontColorPicker = false
                            fontSizeState = !fontSizeState
                        }) {
                            Text(
                                text = "Font",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Button(colors = ButtonDefaults.buttonColors(
                            containerColor = if (backgroundColorPicker) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary
                        ), onClick = {
                            focus.clearFocus()
                            fontSizeState = false
                            fontColorPicker = false
                            backgroundColorPicker = !backgroundColorPicker
                        }) {
                            Text(text = "Background", color = MaterialTheme.colorScheme.primary)
                        }


                    }
                    AnimatedVisibility(visible = fontColorPicker || backgroundColorPicker || fontSizeState) {
                        var titleIsSelected by remember { mutableStateOf(true) }

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (fontSizeState) {
                                Column(
                                    Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                        Button(colors = ButtonDefaults.buttonColors(
                                            containerColor = if (titleIsSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary
                                        ),onClick = {
                                            titleIsSelected = true
                                        }) {
                                            Text(text = "Title", color = MaterialTheme.colorScheme.primary)
                                        }
                                        Spacer(modifier = Modifier.size(8.dp))
                                        Button(colors = ButtonDefaults.buttonColors(
                                            containerColor = if (!titleIsSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary
                                        ),onClick = {
                                            titleIsSelected = false
                                        }) {
                                            Text(text = "Note", color = MaterialTheme.colorScheme.primary)
                                        }
                                    }

                                    ColorPickerPack(
                                        controller = fontController,
                                        state = titleIsSelected
                                    ){
                                        if (titleIsSelected){
                                            pickedTitleColor = it.color.toArgb()
                                        }else{
                                            pickedNoteColor = it.color.toArgb()
                                        }
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "Font size: ${if (titleIsSelected) pickedTitleFontSize.toInt() else pickedNoteFontSize.toInt()}", color = Color.White)
                                        Slider(
                                            value = if (titleIsSelected) pickedTitleFontSize else pickedNoteFontSize,
                                            onValueChange = { if (titleIsSelected) pickedTitleFontSize = it else pickedNoteFontSize = it },
                                            valueRange = 8F..80F,
                                            colors = SliderDefaults.colors(
                                                thumbColor = Color.White,
                                                activeTrackColor = Color.White
                                            )
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceAround
                                    ) {
                                        Button(colors = ButtonDefaults.buttonColors(
                                            containerColor = if (if (titleIsSelected) titleIsItalic else noteIsItalic) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary
                                        ), onClick = {
                                            if (titleIsSelected)
                                            titleIsItalic = !titleIsItalic
                                            else noteIsItalic = !noteIsItalic
                                        }) {
                                            Text(
                                                text = "Italic",
                                                color = MaterialTheme.colorScheme.primary,
                                                fontStyle = FontStyle.Italic
                                            )
                                        }
                                        Button(colors = ButtonDefaults.buttonColors(
                                            containerColor = if (if(titleIsSelected) titleIsBold else noteIsBold) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary
                                        ), onClick = {
                                            if (titleIsSelected)
                                            titleIsBold = !titleIsBold
                                            else noteIsBold = !noteIsBold
                                        }) {
                                            Text(
                                                text = "Bold",
                                                color = MaterialTheme.colorScheme.primary,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Button(colors = ButtonDefaults.buttonColors(
                                            containerColor = if (if (titleIsSelected) titleIsUnderline else noteIsUnderline) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary
                                        ), onClick = {
                                            if (titleIsSelected){
                                                titleIsLineThrough = false
                                                titleIsUnderline = !titleIsUnderline
                                            }else{
                                                noteIsLineThrough = false
                                                noteIsUnderline = !noteIsUnderline
                                            }

                                        }) {
                                            Text(
                                                text = "Underline",
                                                color = MaterialTheme.colorScheme.primary,
                                                textDecoration = TextDecoration.Underline
                                            )
                                        }
                                        Button(colors = ButtonDefaults.buttonColors(
                                            containerColor = if (if (titleIsSelected) titleIsLineThrough else noteIsLineThrough) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary
                                        ), onClick = {
                                            if (titleIsSelected){
                                                titleIsUnderline = false
                                                titleIsLineThrough = !titleIsLineThrough
                                            }else{
                                                noteIsUnderline = false
                                                noteIsLineThrough = !noteIsLineThrough
                                            }

                                        }) {
                                            Text(
                                                text = "Through",
                                                color = MaterialTheme.colorScheme.primary,
                                                textDecoration = TextDecoration.LineThrough
                                            )
                                        }
                                    }

                                }
                            } else if (backgroundColorPicker) {
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    
                                    ColorPickerPack(controller = backgroundController, state = true) {
                                        pickedBackgroundColor = it.color.toArgb()
                                    }
                                }


                            }

                        }
                    }

                    TextField(
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Title",
                                color = Color.LightGray,
                                fontSize = pickedTitleFontSize.sp
                            )
                        },
                        value = titleTf,
                        onValueChange = { titleTf = it },
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .testTag(ADD_EDIT_TITLE_TAG),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color(pickedBackgroundColor),
                            focusedContainerColor = Color(pickedBackgroundColor),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = MaterialTheme.colorScheme.background,
                            selectionColors = TextSelectionColors(
                                handleColor = MaterialTheme.colorScheme.background, backgroundColor = colorResource(
                                    id = R.color.orange
                                )
                            )
                        ),
                        textStyle = TextStyle(
                            fontWeight = if (titleIsBold) FontWeight.Bold else FontWeight.Normal,
                            fontSize = pickedTitleFontSize.sp,
                            color = Color(pickedTitleColor),
                            fontStyle = if (titleIsItalic) FontStyle.Italic else FontStyle.Normal,
                            textDecoration = if (titleIsUnderline) TextDecoration.Underline else if (titleIsLineThrough) TextDecoration.LineThrough else TextDecoration.None
                        )
                    )
                    Divider()

                    TextField(
                        placeholder = {
                            Text(
                                text = "Note",
                                color = Color.LightGray,
                                fontSize = pickedNoteFontSize.sp,
                            )
                        },
                        value = descriptionTf,
                        onValueChange = { descriptionTf = it },
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color(pickedBackgroundColor),
                            focusedContainerColor = Color(pickedBackgroundColor),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = MaterialTheme.colorScheme.background,
                            selectionColors = TextSelectionColors(
                                handleColor = MaterialTheme.colorScheme.background, backgroundColor = colorResource(
                                    id = R.color.orange
                                )
                            ),
                        ),
                        textStyle = TextStyle(
                            fontWeight = if (noteIsBold) FontWeight.Bold else FontWeight.Normal,
                            fontSize = pickedNoteFontSize.sp,
                            color = Color(pickedNoteColor),
                            fontStyle = if (noteIsItalic) FontStyle.Italic else FontStyle.Normal,
                            textDecoration = if (noteIsUnderline) TextDecoration.Underline else if (noteIsLineThrough) TextDecoration.LineThrough else TextDecoration.None
                            )
                        )


                }
                FloatingActionButton(
                    onClick = {
                        focus.clearFocus()
                        if (noteData != null) {
                            if (titleTf.isEmpty() && descriptionTf.isEmpty()) {
                                noteViewModel.deleteNote(noteData)
                            } else {
                                noteViewModel.deleteNote(noteData)
                                noteViewModel.addNote(
                                    noteData = NotesEntity(
                                        noteTitle = titleTf,
                                        noteDescription = descriptionTf,
                                        backgroundColor = pickedBackgroundColor,
                                        title_font_size = pickedTitleFontSize,
                                        note_font_size = pickedNoteFontSize,
                                        title_italic = titleIsItalic,
                                        note_italic = noteIsItalic,
                                        title_bold = titleIsBold,
                                        note_bold = noteIsBold,
                                        title_line_through = titleIsLineThrough,
                                        note_line_through = noteIsLineThrough,
                                        title_underline = titleIsUnderline,
                                        note_underline = noteIsUnderline,
                                        title_color = pickedTitleColor,
                                        note_color = pickedNoteColor
                                    )
                                )
                            }
                        } else {
                            if (titleTf.isNotEmpty() || descriptionTf.isNotEmpty()) {
                                noteViewModel.addNote(
                                    noteData = NotesEntity(
                                        noteTitle = titleTf,
                                        noteDescription = descriptionTf,
                                        backgroundColor = pickedBackgroundColor,
                                        title_font_size = pickedTitleFontSize,
                                        note_font_size = pickedNoteFontSize,
                                        title_italic = titleIsItalic,
                                        note_italic = noteIsItalic,
                                        title_bold = titleIsBold,
                                        note_bold = noteIsBold,
                                        title_line_through = titleIsLineThrough,
                                        note_line_through = noteIsLineThrough,
                                        title_underline = titleIsUnderline,
                                        note_underline = noteIsUnderline,
                                        title_color = pickedTitleColor,
                                        note_color = pickedNoteColor
                                    )
                                )
                            }
                        }
                        navController.navigate("main") {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier
                        .align(alignment = Alignment.BottomEnd)
                        .padding(20.dp)
                        .size(70.dp),
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.save_ic),
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    )

    if (deleteAlert) {
        AlertDialog(
            title = { Text(text = "Do you want to delete?") },
            onDismissRequest = { deleteAlert = false },
            confirmButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background
                ), onClick = {
                    noteViewModel.deleteNote(noteData ?: NotesEntity())
                    navController.popBackStack()
                }) {
                    Text(text = "Yes, delete.", color = Color.White)
                }
            },
            dismissButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background
                ), onClick = {
                    deleteAlert = false
                }) {
                    Text(text = "No, cancel.", color = Color.White)
                }
            })
    }

}


@Composable
fun ColorPickerPack(
    controller: ColorPickerController,
    state: Boolean = false,
    onChange: (ColorEnvelope) -> Unit
) {
    HsvColorPicker(
        modifier = Modifier.size(100.dp),
        controller = controller,
        onColorChanged = {
            if (it.color != Color.White) {
                if (state){
                    onChange(it)
                }
            }
        }
    )
    Spacer(modifier = Modifier.size(14.dp))
    AlphaSlider(
        modifier = Modifier
            .fillMaxWidth()
            .height(25.dp), controller = controller,

    )
    Spacer(modifier = Modifier.size(14.dp))
    BrightnessSlider(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        controller = controller
    )
}