package com.bekzad.currencyconverter

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.bekzad.currencyconverter.data.api.CBRApiService
import com.bekzad.currencyconverter.data.database.CurrencyDao
import com.bekzad.currencyconverter.data.database.CurrencyDatabase
import com.bekzad.currencyconverter.data.repository.CurrencyRepository
import com.bekzad.currencyconverter.ui.MainViewModelFactory


/**
 * @author Бекзад Насирахунов 19/3/22.
 */
object Injection {

    fun provideViewModelFactory(
        activity: FragmentActivity,
    ): MainViewModelFactory {
        return MainViewModelFactory(activity, provideCurrencyRepository(activity.applicationContext))
    }

    @Volatile
    private var currencyRepository: CurrencyRepository? = null

    private fun provideCurrencyRepository(context: Context): CurrencyRepository {
        synchronized(this) {
            return currencyRepository ?: createCurrencyRepository(context)
        }
    }

    private fun createCurrencyRepository(context: Context): CurrencyRepository {
        val newRepo = CurrencyRepository(CBRApiService.api, createDatabase(context))
        currencyRepository = newRepo
        return newRepo
    }

    private fun createDatabase(context: Context): CurrencyDao {
        return CurrencyDatabase.getCurrencyDatabase(context).dao()
    }
}