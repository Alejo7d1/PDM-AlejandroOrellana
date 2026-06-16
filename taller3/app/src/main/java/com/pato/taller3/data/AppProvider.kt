package com.pato.taller3.data

import android.content.Context
import com.pato.taller3.data.database.AppDatabase
import com.pato.taller3.data.repository.OptionRepository
import com.pato.taller3.data.repository.OptionRepositoryImpl
import com.pato.taller3.data.repository.QuestionRepository
import com.pato.taller3.data.repository.QuestionRepositoryImpl

class AppProvider(context: Context) {

    private val appDatabase = AppDatabase.getDatabase(context)
    private val optionDao = appDatabase.optionDao()
    private val questionDao = appDatabase.questionDao()

    private val optionRepository: OptionRepository =
        OptionRepositoryImpl(optionDao)

    private val questionRepository: QuestionRepository =
        QuestionRepositoryImpl(questionDao)

    fun provideOptionRepository(): OptionRepository {
        return optionRepository
    }

    fun provideQuestionRepository(): QuestionRepository {
        return questionRepository
    }
}