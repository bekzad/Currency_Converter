package com.bekzad.currencyconverter.data.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bekzad.currencyconverter.data.domain.Currency


/**
 * @author Бекзад Насирахунов 19/3/22.
 */
@Database(entities = [Currency::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun dao(): CurrencyDao

    companion object {

        @Volatile
        private var INSTANCE: CurrencyDatabase? = null

        fun getCurrencyDatabase(appContext: Context): CurrencyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext,
                    CurrencyDatabase::class.java,
                    "currency_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}