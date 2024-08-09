package com.ttmagic.note.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ttmagic.note.R
import com.ttmagic.note.model.Note
import com.ttmagic.note.ui.model.UIEvent
import com.ttmagic.note.ui.theme.NoteTheme

@Composable
fun NoteDetailScreen(
    note: Note? = null,
    onEvent: (UIEvent) -> Unit,
) {
    val titleMaxLength = 50
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }
    val focusRequester = remember { FocusRequester() }

    Scaffold(modifier = Modifier, floatingActionButton = {
        FloatingActionButton(onClick = {
            onEvent(
                UIEvent.OnAddUpdateNote(
                    note?.copy(title = title, content = content)
                        ?: Note(title = title, content = content)
                )
            )

        }) {
            Text(
                text = stringResource(id = if (note == null) R.string.add_note else R.string.update_note),
                Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }, content = { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row {
                IconButton(modifier = Modifier
                    .size(50.dp), onClick = {
                    onEvent(UIEvent.OnClickNavBack)
                }) {
                    Icon(
                        modifier = Modifier.padding(12.dp),
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
                TextField(
                    value = title,
                    singleLine = true,
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    onValueChange = {
                        if (it.length <= titleMaxLength) title = it
                    },
                    placeholder = {
                        Text(stringResource(id = R.string.title))
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }

            TextField(value = content,
                modifier = Modifier.fillMaxSize(),
                onValueChange = {
                    content = it
                },
                placeholder = {
                    Text(stringResource(id = R.string.content))
                })
        }
    })

    LaunchedEffect(Unit) {
        if (note == null) {
            focusRequester.requestFocus()
        }
    }
}


@Preview
@Composable
fun PreviewNoteDetailScreenAdd() {
    NoteTheme {
        NoteDetailScreen(onEvent = {})
    }
}

@Preview
@Composable
fun PreviewNoteDetailScreenUpdate() {
    NoteTheme {
        NoteDetailScreen(note = Note(title = "Abc", content = "Note\ncontent\nmultiple\nlines"),
            onEvent = {})
    }
}