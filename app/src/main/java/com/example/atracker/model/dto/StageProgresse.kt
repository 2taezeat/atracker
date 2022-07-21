package com.example.atracker.model.dto

data class StageProgresse(
    val deleted_contents: List<DeletedContent>,
    val id: Int,
    val new_contents: List<NewContent>,
    val status: String,
    val updated_contents: List<UpdatedContent>
)