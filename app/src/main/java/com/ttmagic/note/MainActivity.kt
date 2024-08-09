package com.ttmagic.note

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ttmagic.note.model.Note
import com.ttmagic.note.ui.NoteDetailScreen
import com.ttmagic.note.ui.NoteListScreen
import com.ttmagic.note.ui.NoteViewModel
import com.ttmagic.note.ui.model.Route
import com.ttmagic.note.ui.model.UIEvent
import com.ttmagic.note.ui.theme.NoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<NoteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteAppScreen(viewModel)
        }
    }
}

@Composable
fun NoteAppScreen(viewModel: NoteViewModel) {
    val navController = rememberNavController()
    NoteTheme {
        NavHost(
            navController = navController, startDestination = Route.NoteList
        ) {
            composable<Route.NoteList> {
                BuildNoteListScreen(navController, viewModel)
            }
            composable<Route.NoteDetail> {
                val args = it.toRoute<Route.NoteDetail>()
                val note = viewModel.getNote(args.noteId)
                BuildNoteDetailScreen(note, viewModel, navController)
            }
        }
    }
}

@Composable
private fun BuildNoteDetailScreen(
    note: Note?, viewModel: NoteViewModel, navController: NavHostController
) {
    val context = LocalContext.current
    NoteDetailScreen(note, onEvent = { uiEvent ->
        when (uiEvent) {
            is UIEvent.OnClickNavBack -> navController.navigateUp()
            is UIEvent.OnAddUpdateNote -> {
                viewModel.addUpdateNote(uiEvent.note) { isSuccess ->
                    if (isSuccess) {
                        navController.popBackStack()
                    } else {
                        val msg = context.resources.getText(R.string.error_note_blank)
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            else -> {}
        }
    })
}

@Composable
private fun BuildNoteListScreen(navController: NavHostController, viewModel: NoteViewModel) {
    val notes = viewModel.notes.observeAsState()
    NoteListScreen(notes = notes.value ?: emptyList(), onEvent = { uiEvent ->
        when (uiEvent) {
            is UIEvent.OnClickAddNote -> navController.navigate(Route.NoteDetail())
            is UIEvent.OnClickNote -> navController.navigate(Route.NoteDetail(uiEvent.note.id))
            is UIEvent.OnClickDeleteNote -> viewModel.deleteNote(uiEvent.note)
            else -> {}
        }
    })
}