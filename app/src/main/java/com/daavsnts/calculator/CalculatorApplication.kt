package com.daavsnts.calculator

import android.app.Application
import com.daavsnts.calculator.data.database.room.OperationDatabase
import com.daavsnts.calculator.data.repository.OperationRepository

class CalculatorApplication: Application() {
    private val database: OperationDatabase by lazy { OperationDatabase.getDatabase(this) }
    val repository: OperationRepository by lazy { OperationRepository(database.operationDao()) }
}