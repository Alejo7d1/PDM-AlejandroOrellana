package com.pato.taller3.data.repository

import com.pato.taller3.data.model.Option
import kotlinx.coroutines.flow.Flow

interface OptionRepository {
    fun getOptions(questionId: Int): Flow<List<Option>>
    suspend fun addOption(option: String, imageUrl: String, questionId: Int)
    suspend fun deleteOption(option: Option)
}
