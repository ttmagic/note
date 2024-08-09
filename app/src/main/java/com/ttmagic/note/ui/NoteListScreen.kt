package com.ttmagic.note.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ttmagic.note.R
import com.ttmagic.note.model.Note
import com.ttmagic.note.ui.model.UIEvent
import com.ttmagic.note.ui.theme.NoteTheme
import com.ttmagic.note.ui.view.AppDialog
import com.ttmagic.note.ui.view.NoteItem

@Composable
fun NoteListScreen(
    notes: List<Note>,
    onEvent: (UIEvent) -> Unit,
) {
    val noteToDelete = remember { mutableStateOf<Note?>(null) }

    Scaffold(modifier = Modifier.padding(8.dp), floatingActionButton = {
        FloatingActionButton(onClick = { onEvent(UIEvent.OnClickAddNote) }) {
            Icon(
                imageVector = Icons.Filled.Add, contentDescription = null
            )
        }
    }, content = { innerPadding ->
        if (notes.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(
                    text = stringResource(id = R.string.note_empty),
                    textAlign = TextAlign.Center,
                )
            }
            return@Scaffold
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notes) { note ->
                NoteItem(
                    note = note,
                    onClickNote = { onEvent(UIEvent.OnClickNote(note)) },
                    onDeleteNote = { noteToDelete.value = it })
            }
        }

        noteToDelete.value?.let { note ->
            AppDialog(onDismiss = {
                noteToDelete.value = null
            }, title = R.string.confirm_delete_note, onClickConfirm = {
                onEvent(UIEvent.OnClickDeleteNote(note))
            })
        }
    })
}


@Preview(showBackground = true)
@Composable
fun NoteListScreenPreview() {
    val notes = arrayListOf(
        Note(title = "Abc", content = "Content"),
        Note(title = "Xyz", content = "Note 2")
    )
    NoteTheme {
        NoteListScreen(notes, onEvent = {})
    }
}

@Preview(showBackground = true)
@Composable
fun NoteListScreenEmptyPreview() {
    NoteTheme {
        NoteListScreen(emptyList(), onEvent = {})
    }
}