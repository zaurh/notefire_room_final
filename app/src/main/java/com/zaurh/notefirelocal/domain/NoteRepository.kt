package com.zaurh.notefirelocal.domain

import com.zaurh.notefirelocal.data.local.NotesEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository
{
    fun getAllNotes(): Flow<List<NotesEntity>>

    suspend fun addNote(noteData : NotesEntity)

    suspend fun deleteNote(noteData: NotesEntity)

    fun searchNote(query: String): Flow<List<NotesEntity>>
}