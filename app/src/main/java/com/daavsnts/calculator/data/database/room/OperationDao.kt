package com.daavsnts.calculator.data.database.room

import androidx.room.*
import com.daavsnts.calculator.model.Operation
import kotlinx.coroutines.flow.Flow

@Dao
interface OperationDao {
    @Query("SELECT * FROM operation_table ORDER BY id DESC")
    fun allOperations(): Flow<List<Operation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(operation: Operation)

    @Insert
    suspend fun insertAll(vararg operations: Operation)

    @Delete
    suspend fun delete(operation: Operation)

    @Query("DELETE FROM operation_table")
    suspend fun deleteAll()
}