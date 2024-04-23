package com.zaurh.notefirelocal.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zaurh.notefirelocal.data.local.NotesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert
    suspend fun insert(note: NotesEntity)

    @Delete
    suspend fun delete(note: NotesEntity)

    @Query("SELECT * FROM notes")
    fun allNotes(): Flow<List<NotesEntity>>

    @Query("SELECT * FROM notes WHERE note_title || note_description like '%' || :query || '%'")
    fun searchNote(query: String): Flow<List<NotesEntity>>
}