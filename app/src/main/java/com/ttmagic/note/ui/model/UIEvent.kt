package com.ttmagic.note.ui.model

import com.ttmagic.note.model.Note

sealed class UIEvent {
    data object OnClickNavBack : UIEvent()
    data object OnClickAddNote : UIEvent()
    data class OnClickNote(val note: Note) : UIEvent()
    data class OnClickDeleteNote(val note: Note) : UIEvent()
    data class OnAddUpdateNote(val note: Note) : UIEvent()

}