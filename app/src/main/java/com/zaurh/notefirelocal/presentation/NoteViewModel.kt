package com.zaurh.notefirelocal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaurh.notefirelocal.data.local.NotesEntity
import com.zaurh.notefirelocal.domain.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {



    fun addNote(noteData: NotesEntity) {
        viewModelScope.launch {
            repository.addNote(noteData)
        }
    }

    fun deleteNote(noteData: NotesEntity) {
        viewModelScope.launch {
            repository.deleteNote(noteData)
        }
    }

    fun searchNotes(query: String): Flow<List<NotesEntity>> {
        return repository.searchNote(query)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList()
            )
    }

}

