package com.bekzad.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import com.bekzad.currencyconverter.data.api.CBRApiService
import com.bekzad.currencyconverter.data.api.CurrencyResponse
import com.bekzad.currencyconverter.data.api.asDomainModel
import com.bekzad.currencyconverter.data.database.CurrencyDao
import com.bekzad.currencyconverter.data.domain.Currency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


/**
 * @author Бекзад Насирахунов 19/3/22.
 */
class CurrencyRepository(
    private val api: CBRApiService,
    private val dao: CurrencyDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    val currencies: LiveData<List<Currency>>
        get() = dao.getAllCurrencies()

    suspend fun refreshCurrencies() = withContext(dispatcher) {
        val networkCurrencies = api.getAllCurrencies().currencies
        dao.insert(networkCurrencies.asDomainModel())
    }
}