package com.ttmagic.note.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ttmagic.note.model.Note
import com.ttmagic.note.ui.theme.NoteTheme
import com.ttmagic.note.ui.theme.Typography

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    onClickNote: (Note) -> Unit,
    onDeleteNote: (Note) -> Unit
) {
    Card(modifier = modifier
        .fillMaxWidth()
        .clickable {
            onClickNote(note)
        }

    ) {
        Box(
            modifier = modifier.fillMaxWidth()
        ) {
            IconButton(modifier = modifier
                .size(50.dp)
                .align(Alignment.TopEnd), onClick = {
                onDeleteNote(note)
            }) {
                Icon(
                    modifier = modifier.padding(12.dp),
                    imageVector = Icons.Filled.Close,
                    contentDescription = null
                )
            }

            Column {
                Text(
                    modifier = modifier
                        .padding(8.dp, 12.dp, 50.dp, 8.dp)
                        .fillMaxWidth(),
                    style = Typography.titleLarge,
                    text = note.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = modifier
                        .padding(8.dp, 0.dp, 8.dp, 12.dp)
                        .fillMaxWidth(),
                    style = Typography.bodyLarge,
                    text = note.content,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewNoteItem() {
    NoteTheme {
        NoteItem(note = Note(
            title = "Title", content = "Note content"
        ), onClickNote = {}, onDeleteNote = {})
    }
}