package com.example.data.model

data class Note(
    var title: String = "",
    var description: String = "",
    val id: String,
) {
    val titleForList: String
        get() = title.ifEmpty { description }

    val isEmpty
        get() = title.isEmpty() || description.isEmpty()
}