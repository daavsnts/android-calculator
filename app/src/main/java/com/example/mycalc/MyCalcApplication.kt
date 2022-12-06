package com.example.mycalc

import android.app.Application
import com.example.mycalc.data.database.room.OperationDatabase
import com.example.mycalc.data.repository.OperationRepository

class MyCalcApplication: Application() {
    private val database: OperationDatabase by lazy { OperationDatabase.getDatabase(this) }
    val repository: OperationRepository by lazy { OperationRepository(database.operationDao()) }
}