package com.ttmagic.note.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttmagic.note.model.Note
import com.ttmagic.note.usecase.AddUpdateNoteUseCase
import com.ttmagic.note.usecase.DeleteNoteUseCase
import com.ttmagic.note.usecase.LoadNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val loadNoteUseCase: LoadNoteUseCase,
    private val addUpdateNoteUseCase: AddUpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {
    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("NoteViewModel", throwable.message ?: "")
    }

    private val _notes by lazy { MutableLiveData<List<Note>>() }
    val notes: LiveData<List<Note>> get() = _notes

    init {
        loadNote()
    }

    fun loadNote() = viewModelScope.launch(handler) {
        val notes = loadNoteUseCase.execute()
        _notes.value = notes
    }

    fun addUpdateNote(note: Note, onComplete: (Boolean) -> Unit) = viewModelScope.launch(handler) {
        if (note.content.isBlank() || note.title.isBlank()) {
            onComplete(false)
            return@launch
        }
        addUpdateNoteUseCase.execute(note)
        loadNote()
        onComplete(true)
    }

    fun deleteNote(note: Note) = viewModelScope.launch(handler) {
        deleteNoteUseCase.execute(note)
        loadNote()
    }

    fun getNote(noteId: Int): Note? {
        return notes.value?.firstOrNull { it.id == noteId }
    }
}