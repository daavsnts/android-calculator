package com.daavsnts.calculator.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "operation_table")
data class Operation(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "first_operand") var firstOperand: String?,
    @ColumnInfo(name = "operator") var operator: String?,
    @ColumnInfo(name = "second_operand") var secondOperand: String?,
    @ColumnInfo(name = "operation_result") var operationResult: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(firstOperand)
        parcel.writeString(operator)
        parcel.writeString(secondOperand)
        parcel.writeString(operationResult)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Operation> {
        override fun createFromParcel(parcel: Parcel): Operation {
            return Operation(parcel)
        }

        override fun newArray(size: Int): Array<Operation?> {
            return arrayOfNulls(size)
        }
    }
}