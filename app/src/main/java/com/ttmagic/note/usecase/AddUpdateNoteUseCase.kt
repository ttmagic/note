package com.ttmagic.note.usecase

import com.ttmagic.note.model.Note
import com.ttmagic.note.repo.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddUpdateNoteUseCase @Inject constructor(private val noteDao: NoteDao) {

    suspend fun execute(note: Note) = withContext(Dispatchers.IO) {
        noteDao.addNote(note)
    }
}