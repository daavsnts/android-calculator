package com.example.mycalc.data.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mycalc.model.Operation

@Database(entities = [Operation::class], version = 1, exportSchema = false)
abstract class OperationDatabase : RoomDatabase() {
    abstract fun operationDao(): OperationDao

    companion object {
        @Volatile
        private var INSTANCE: OperationDatabase? = null

        fun getDatabase(context: Context): OperationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    OperationDatabase::class.java,
                    "operation_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

