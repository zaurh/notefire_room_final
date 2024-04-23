package com.zaurh.notefirelocal.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zaurh.notefirelocal.common.NavParam
import com.zaurh.notefirelocal.common.convertDate
import com.zaurh.notefirelocal.common.navigateTo
import com.zaurh.notefirelocal.data.local.NotesEntity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteListItem(
    navController: NavController,
    noteData: NotesEntity
) {
    val focus = LocalFocusManager.current

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp)
        .clip(RoundedCornerShape(10.dp))
        .clickable {
            navigateTo(navController, "edit_note", NavParam("noteData", noteData))
            focus.clearFocus()
        }
        .background(Color.White)


    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
                .height(150.dp)
                .background(
                    Color(noteData.backgroundColor)
                )
                .padding(5.dp)
        ) {
            Text(
                text = convertDate(noteData.date, "dd MMMM  HH:mm"),
                fontSize = 12.sp,
                color = if (Color(noteData.backgroundColor).luminance() > 0.5) Color.Gray else Color.LightGray
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = noteData.noteTitle,
                fontWeight = if (noteData.title_bold) FontWeight.Bold else FontWeight.Normal,
                textDecoration = if (noteData.title_underline) TextDecoration.Underline else if (noteData.title_line_through) TextDecoration.LineThrough else TextDecoration.None,
                fontStyle = if (noteData.title_italic) FontStyle.Italic else FontStyle.Normal,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = if (noteData.title_font_size > 60) 32.sp else if (noteData.title_font_size > 30) 26.sp else 18.sp,
                color = Color(noteData.title_color)
            )
            Text(
                text = noteData.noteDescription,
                fontWeight = if (noteData.note_bold) FontWeight.Bold else FontWeight.Normal,
                textDecoration = if (noteData.note_underline) TextDecoration.Underline else if (noteData.note_line_through) TextDecoration.LineThrough else TextDecoration.None,
                fontStyle = if (noteData.note_italic) FontStyle.Italic else FontStyle.Normal,
                fontSize = if (noteData.note_font_size > 60) 28.sp else if (noteData.note_font_size > 30) 22.sp else 14.sp,
                overflow = TextOverflow.Ellipsis,
                color = Color(noteData.note_color)
            )

        }
    }


}
