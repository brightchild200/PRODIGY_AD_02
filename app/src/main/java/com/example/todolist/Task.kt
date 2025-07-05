package com.example.todolist

/** Firestore → POJO. Keep field names lowercase to match the document. */
data class Task(
    var id: String = "",             // document id (set locally)
    var description: String = "",
    var completed: Boolean = false
)
