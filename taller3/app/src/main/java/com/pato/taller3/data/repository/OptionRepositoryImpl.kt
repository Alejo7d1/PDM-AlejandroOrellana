package com.pato.taller3.data.repository

import com.pato.taller3.data.database.dao.OptionDao
import com.pato.taller3.data.database.entities.QuestionEntity
import com.pato.taller3.data.database.entities.toModel
import com.pato.taller3.data.model.Option
import com.pato.taller3.data.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OptionRepositoryImpl(
    private val optionDao: OptionDao
) : OptionRepository {

            override fun getOptions(questionId: Int): Flow<List<Option>> {
                return optionDao.getOptionsForQuestion(questionId).map { entities ->
                    entities.map { it.toModel() }
                }
            }override suspend fun addOption(name: String, imageUrl: String, questionId: Int) {
                    val option = Option(name = name, imageUrl = imageUrl, questionId = questionId)
                    optionDao.insertOption(option.toEntity())
                }

                override suspend fun deleteOption(option: Option) {
                    optionDao.deleteOption(option.toEntity())
                }
            }
