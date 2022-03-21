package com.bekzad.currencyconverter.ui

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.bekzad.currencyconverter.data.domain.Currency
import com.bekzad.currencyconverter.data.repository.CurrencyRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

enum class Status { LOADING, ERROR, DONE }

/**
 * @author Бекзад Насирахунов 19/3/22.
 *
 */
class MainViewModel(private val savedState: SavedStateHandle,
                    private val repository: CurrencyRepository
) : ViewModel(), DefaultLifecycleObserver {

    private val handler = Handler(Looper.getMainLooper())

    val currencies: LiveData<List<Currency>> = repository.currencies
    val status = MutableLiveData<Status>()

    private var selectedCurrency: Currency? = savedState[SELECTED_CURRENCY_KEY]
    private var count: BigDecimal = savedState[COUNT_KEY] ?: BigDecimal.ONE
    val resultText = MutableLiveData<String>()

    init {
        refreshUI()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        savedState[SELECTED_CURRENCY_KEY] = selectedCurrency
        savedState[COUNT_KEY] = count
    }

    fun refreshUI() {
        viewModelScope.launch {
            status.value = Status.LOADING
            try {
                repository.refreshCurrencies()
                status.value = Status.DONE
            } catch (exception: Exception) {
                status.value = Status.ERROR
            }
        }
        handler.postDelayed(REFRESH_INTERVAL_MILLS) {
            refreshUI()
        }
    }

    fun updateSelectedCurrency(currency: Currency) {
        selectedCurrency = currency
        updateResult()
    }

    fun updateCount(countValue: String?) {
        count = if (countValue.isNullOrBlank()) { BigDecimal.ONE }
        else { countValue.toBigDecimal() }
        updateResult()
    }

    private fun updateResult() {
        val currency = selectedCurrency ?: return
        val currencyValue = currency.value.toBigDecimal()
        val currencyNominal = currency.nominal.toBigDecimal()
        val result = count.multiply(currencyValue.divide(currencyNominal))
        resultText.value = "${result.setScale(2, RoundingMode.HALF_EVEN)} RUB"
    }

    companion object {
        private const val REFRESH_INTERVAL_MILLS = 20000L
        private const val COUNT_KEY = "COUNT_KEY"
        private const val SELECTED_CURRENCY_KEY = "SELECTED_CURRENCY_KEY"
    }

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: CurrencyRepository
) : AbstractSavedStateViewModelFactory(owner, null) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return MainViewModel(handle, repository) as T
    }
}