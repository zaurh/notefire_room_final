package com.zaurh.notefirelocal.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.zaurh.notefirelocal.data.local.NotesEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NotesDaoTest {

    private lateinit var database: NotesDatabase
    private lateinit var dao: NotesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NotesDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.notesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insert_note_and_check_if_it_is_on_the_list() = runBlocking {
        val note = NotesEntity(
            1,
            "hello",
            "world",
            1,
            1,
            1,
            1f,
            1f
        )

        dao.insert(note)

        dao.allNotes().take(1).collect { allNotes ->
            assert(note in allNotes)
        }
    }

    @Test
    fun delete_note_and_check_it_is_not_on_the_list() = runBlocking {
        val note = NotesEntity(
            1,
            "hello",
            "world",
            1,
            1,
            1,
            1f,
            1f
        )
        dao.insert(note)
        dao.delete(note)

        dao.allNotes().take(1).collect { allNotes ->
            assert(note !in allNotes)
        }
    }

    @Test
    fun search_note_and_check_if_it_brings_you_correct_note() = runBlocking {
        val note = NotesEntity(
            1,
            "hello",
            "world",
            1,
            1,
            1,
            1f,
            1f
        )
        val note2 = NotesEntity(
            1,
            "testing",
            "searching",
            1,
            1,
            1,
            1f,
            1f
        )
        dao.insert(note)
        dao.insert(note2)

        dao.searchNote("testing").take(1).collect { allNotes ->
            assert(note2 in allNotes)
        }
    }




}