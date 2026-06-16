package com.pato.taller3.data.repository

import com.pato.taller3.data.model.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    fun getQuestions(): Flow<List<Question>>
    suspend fun addQuestion(question: Question)
    suspend fun deleteQuestion(question: Question)
}
