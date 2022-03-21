package com.bekzad.currencyconverter.data.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


/**
 * @author Бекзад Насирахунов 19/3/22.
 */
@Parcelize
@Entity(tableName = "currency_table")
data class Currency(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "char_code") val charCode: String,
    @ColumnInfo(name = "nominal") val nominal: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "value") val value: String
) : Parcelable