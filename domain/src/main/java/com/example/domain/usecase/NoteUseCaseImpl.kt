package com.example.domain.usecase

import com.example.core.util.di.ApplicationScope
import com.example.core.util.di.DefaultDispatcher
import com.example.data.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

//class NoteUseCaseImpl @Inject constructor(
//    private val noteRepository: NoteRepository,
//    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
//    @ApplicationScope private val scope: CoroutineScope,
//): NoteUseCase{
//    operator fun invoke(){
//
//    }
//}