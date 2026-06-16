package com.pato.taller3.data.repository

import com.pato.taller3.data.database.dao.QuestionDao
import com.pato.taller3.data.database.entities.QuestionEntity
import com.pato.taller3.data.database.entities.toModel
import com.pato.taller3.data.model.Question
import com.pato.taller3.data.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuestionRepositoryImpl(private val questionDao: QuestionDao) : QuestionRepository {
    override fun getQuestions(): Flow<List<Question>> =
        questionDao.getQuestionsWithOptions().map { entities ->
            entities.map { relation ->
                relation.question.toModel().copy(
                    optionCount = relation.options.size
                )
            }
        }

    override suspend fun addQuestion(question: Question) =
        questionDao.insertQuestion(question.toEntity())

    override suspend fun deleteQuestion(question: Question) =
        questionDao.deleteQuestion(question.toEntity())
}
