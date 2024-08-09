package com.ttmagic.note.ui.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object NoteList : Route()

    @Serializable
    data class NoteDetail(val noteId: Int = 0) : Route()
}