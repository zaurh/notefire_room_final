@file:OptIn(ExperimentalMaterial3Api::class)

package com.zaurh.notefirelocal.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import com.zaurh.notefirelocal.common.TestTags.SEARCH_BAR

@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    text: String,
    onSearch: (String) -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    Box(modifier = modifier) {
        TextField(
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "")},
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            placeholder = { Text(text = "Search...")},
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            value = text,
            onValueChange = {
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .testTag(SEARCH_BAR)
        )
    }
}

