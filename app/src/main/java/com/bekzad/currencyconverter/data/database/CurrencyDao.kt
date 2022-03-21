package com.bekzad.currencyconverter.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bekzad.currencyconverter.data.domain.Currency


/**
 * @author Бекзад Насирахунов 19/3/22.
 */
@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency_table")
    fun getAllCurrencies(): LiveData<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencies: List<Currency>)
}