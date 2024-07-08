package com.example.core.util.eventbus

sealed class MainEvent {
    class Detail(val noteId: String? = null) : MainEvent()

    object Undefined : MainEvent()
}