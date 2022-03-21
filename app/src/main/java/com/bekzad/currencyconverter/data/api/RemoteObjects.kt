package com.bekzad.currencyconverter.data.api

import com.bekzad.currencyconverter.data.domain.Currency
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * @author Бекзад Насирахунов 19/3/22.
 */
@JsonClass(generateAdapter = true)
data class CurrencyResponse(
    @Json(name = "Valute") val currencies: Map<String, NetworkCurrency>
)

@JsonClass(generateAdapter = true)
data class NetworkCurrency(
    @Json(name = "ID") val id: String,
    @Json(name = "CharCode") val charCode: String,
    @Json(name = "Nominal") val nominal: Int,
    @Json(name = "Name") val name: String,
    @Json(name = "Value") val value: String
)

fun Map<String, NetworkCurrency>.asDomainModel(): List<Currency> {
    return this.values.map { value ->
        Currency(
            id = value.id,
            charCode = value.charCode,
            nominal = value.nominal,
            name = value.name,
            value = value.value
        )
    }
}