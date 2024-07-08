package com.example.core.util.eventbus

import android.os.SystemClock
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object MainEventDispatcher {
    private const val DISPATCH_THRESHOLD = 2000L
    private var lastTimeDispatched: Long = 0
    private var lastEvent: MainEvent = MainEvent.Undefined

    private val dispatcher = MutableSharedFlow<MainEvent>()
    val listener = dispatcher.asSharedFlow()

    suspend fun dispatchEvent(event: MainEvent) = dispatcher.emit(event)

    suspend fun dispatchSingleEvent(event: MainEvent) {
        if (event::class == lastEvent::class && SystemClock.elapsedRealtime() - lastTimeDispatched < DISPATCH_THRESHOLD) return
        lastTimeDispatched = SystemClock.elapsedRealtime()
        lastEvent = event
        dispatcher.emit(event)
    }
}