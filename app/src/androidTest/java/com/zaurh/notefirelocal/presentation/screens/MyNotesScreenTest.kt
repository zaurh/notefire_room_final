package com.zaurh.notefirelocal.presentation.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import com.zaurh.notefirelocal.MainActivity
import com.zaurh.notefirelocal.common.TestTags.ACTION_BUTTON_MAIN_SCREEN
import com.zaurh.notefirelocal.common.TestTags.ADD_EDIT_TITLE_TAG
import com.zaurh.notefirelocal.common.TestTags.ADD_NOTE_FLOATING_BUTTON
import com.zaurh.notefirelocal.common.TestTags.DARK_THEME_STATE
import com.zaurh.notefirelocal.common.TestTags.DROPDOWN_MENU_MAIN_SCREEN
import com.zaurh.notefirelocal.common.TestTags.GRID_CELLS_SIZE
import com.zaurh.notefirelocal.common.TestTags.SEARCH_BAR
import com.zaurh.notefirelocal.common.TestTags.TITLE_BAR_MAIN_SCREEN
import com.zaurh.notefirelocal.data.local.NotesEntity
import org.junit.Rule
import org.junit.Test


class NotesScreenTest {

    private lateinit var navController: TestNavHostController

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    val mockNoteList = listOf(
        NotesEntity(
            noteId = 1,
            noteTitle = "testing_hello",
            noteDescription = "testing_description"
        ),
        NotesEntity(
            noteId = 2,
            noteTitle = "testing_hello2",
            noteDescription = "testing_description2"
        )
    )

    @Test
    fun title_bar_text_is_my_notes(){
        composeRule.onNodeWithTag(TITLE_BAR_MAIN_SCREEN).assertTextEquals("My Notes")
    }

    @Test
    fun there_is_a_search_bar(){
        composeRule.onNodeWithTag(SEARCH_BAR).assertExists()
    }

    @Test
    fun floating_button_exists_and_clicking_it_navigates_to_add_screen(){
        composeRule.onNodeWithTag(ADD_NOTE_FLOATING_BUTTON).assertExists().performClick()
        composeRule.onNodeWithTag(ADD_EDIT_TITLE_TAG).assertIsDisplayed()
    }

    @Test
    fun three_dot_exists_and_clicking_it_makes_appear_dropdown(){
        composeRule.onNodeWithTag(DROPDOWN_MENU_MAIN_SCREEN).assertDoesNotExist()
        composeRule.onNodeWithTag(ACTION_BUTTON_MAIN_SCREEN).assertExists().performClick()
        composeRule.onNodeWithTag(DROPDOWN_MENU_MAIN_SCREEN).assertIsDisplayed()
    }

    @Test
    fun clicking_grid_cells_changes_grid_size() {
        composeRule.onNodeWithTag(ACTION_BUTTON_MAIN_SCREEN).performClick()
        composeRule.onNodeWithTag(GRID_CELLS_SIZE, useUnmergedTree = true)
            .assertIsDisplayed()
            .assertTextEquals("2")
            .performClick()
            .assertTextEquals("3")
            .performClick()
            .assertTextEquals("1")
            .performClick()
            .assertTextEquals("2")
    }

    @Test
    fun clicking_dark_theme_changes_theme(){
        composeRule.onNodeWithTag(ACTION_BUTTON_MAIN_SCREEN).performClick()
        composeRule.onNodeWithTag(DROPDOWN_MENU_MAIN_SCREEN).assertIsDisplayed()
        composeRule.onNodeWithTag(DARK_THEME_STATE).assertExists().assertTextEquals("Dark theme").performClick()
        composeRule.onNodeWithTag(DARK_THEME_STATE).assertTextEquals("Light theme")
    }

}