package com.cmc.atracker.model.dto

data class StageProgresse(
    val deleted_contents: ArrayList<DeletedContent>,
    val id: Int,
    val new_contents: ArrayList<NewContent>,
    var status: String,
    val updated_contents: ArrayList<UpdatedContent>
)