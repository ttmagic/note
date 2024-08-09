package com.ttmagic.note.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ttmagic.note.MainCoroutineRule
import com.ttmagic.note.model.Note
import com.ttmagic.note.usecase.AddUpdateNoteUseCase
import com.ttmagic.note.usecase.DeleteNoteUseCase
import com.ttmagic.note.usecase.LoadNoteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NoteViewModelTest {
    private lateinit var noteViewModel: NoteViewModel
    private val loadNoteUseCase: LoadNoteUseCase = mock()
    private val addUpdateNoteUseCase: AddUpdateNoteUseCase = mock()
    private val deleteNoteUseCase: DeleteNoteUseCase = mock()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        noteViewModel = NoteViewModel(
            loadNoteUseCase = loadNoteUseCase,
            addUpdateNoteUseCase = addUpdateNoteUseCase,
            deleteNoteUseCase = deleteNoteUseCase,
        )
    }


    @Test
    fun test_loadNote() = runTest {
        // Arrange
        val mockedNotes = arrayListOf(Note(1, "title", "content"))
        whenever(loadNoteUseCase.execute()).thenReturn(mockedNotes)

        // Act
        noteViewModel.loadNote()

        // Assert
        assertTrue(noteViewModel.notes.value?.isNotEmpty() == true)
    }

    @Test
    fun test_addUpdateNote_empty_note() = runTest {
        val note = Note(title = "", content = "")
        whenever(addUpdateNoteUseCase.execute(note)).thenReturn(Unit)

        noteViewModel.addUpdateNote(note) {}

        // Should not call UseCase if note is empty
        verify(addUpdateNoteUseCase, never()).execute(note)
    }

    @Test
    fun test_addUpdateNote_case_success() = runTest {
        val note = Note(title = "Title", content = "Content")
        whenever(addUpdateNoteUseCase.execute(note)).thenReturn(Unit)

        noteViewModel.addUpdateNote(note) {}

        // Should call addUpdateNoteUseCase
        verify(addUpdateNoteUseCase, atLeastOnce()).execute(note)
    }

    @Test
    fun test_deleteNote() = runTest {
        val note = Note(title = "Title", content = "Content")

        noteViewModel.deleteNote(note)

        // Should call deleteNoteUseCase
        verify(deleteNoteUseCase, atLeastOnce()).execute(note)
    }

    @Test
    fun test_getNote() = runTest {
        val mockedNotes = arrayListOf(Note(1, "title", "content"))
        whenever(loadNoteUseCase.execute()).thenReturn(mockedNotes)

        // Act
        noteViewModel.loadNote()
        val result = noteViewModel.getNote(1)

        assertEquals(result, mockedNotes.first())
    }
}