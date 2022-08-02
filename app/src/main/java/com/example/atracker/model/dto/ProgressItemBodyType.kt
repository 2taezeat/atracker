package com.example.atracker.model.dto

enum class ProgressItemBodyType(val order :Int) {
    OVERALL(0), // REVIEW, 종합 후기
    QNA(1),
    FREE_FORM(2), // FREE
    NOT_DEFINED(3),
}

//REVIEW,
//QNA,
//FREE