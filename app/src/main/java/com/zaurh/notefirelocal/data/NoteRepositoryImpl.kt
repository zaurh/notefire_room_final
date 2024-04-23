package com.zaurh.notefirelocal.data

import com.zaurh.notefirelocal.data.local.NotesEntity
import com.zaurh.notefirelocal.domain.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NotesDao
) : NoteRepository {

    override suspend fun addNote(noteData: NotesEntity) = dao.insert(noteData)

    override suspend fun deleteNote(noteData: NotesEntity) = dao.delete(noteData)

    override fun getAllNotes(): Flow<List<NotesEntity>> {
        return dao.allNotes().flowOn(Dispatchers.IO)
    }

    override fun searchNote(query: String): Flow<List<NotesEntity>> {
        return dao.searchNote(query).flowOn(Dispatchers.IO)
    }
}