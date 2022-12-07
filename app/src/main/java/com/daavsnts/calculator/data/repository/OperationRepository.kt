package com.daavsnts.calculator.data.repository

import androidx.annotation.WorkerThread
import com.daavsnts.calculator.data.database.room.OperationDao
import com.daavsnts.calculator.model.Operation
import kotlinx.coroutines.flow.Flow

class OperationRepository(private val operationDao: OperationDao) {
    val allOperations: Flow<List<Operation>> = operationDao.allOperations()

    @WorkerThread
    suspend fun insert(operation: Operation) = operationDao.insert(operation)

    @WorkerThread
    suspend fun delete(operation: Operation) = operationDao.delete(operation)

    @WorkerThread
    suspend fun deleteAll() = operationDao.deleteAll()
}