package com.pdm.labo5.model

import java.util.Date
data class Task (
    val id: Int = 0,
    val title: String,
    val description: String,
    val endDate: Date,
    val isCompleted: Boolean
)

