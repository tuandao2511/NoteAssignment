package com.example.data.model

import com.example.data.entity.NoteEntity


fun NoteEntity.toNote() = Note(
    id = id,
    title = title,
    description = description
)

fun Note.toNoteEntity() = NoteEntity(
    id = id,
    title = title,
    description = description
)

fun List<NoteEntity>.toListNote() = map(NoteEntity::toNote)
