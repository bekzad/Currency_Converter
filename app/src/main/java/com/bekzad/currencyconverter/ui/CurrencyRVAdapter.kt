package com.bekzad.currencyconverter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bekzad.currencyconverter.data.domain.Currency
import com.bekzad.currencyconverter.databinding.ItemRecyclerViewBinding


/**
 * @author Бекзад Насирахунов 19/3/22.
 */
class CurrencyRVAdapter : ListAdapter<Currency, CurrencyRVAdapter.CurrencyViewHolder>(CurrencyItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder.getInstance(parent)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = getItem(position)
        holder.bind(currency)
    }

    class CurrencyViewHolder private constructor(private val vb: ItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(vb.root) {

        fun bind(currency: Currency) {
            vb.apply {
                nominal.text = "${currency.nominal}"
                name.text = currency.name
                symbol.text = currency.charCode
                value.text = currency.value
            }
        }

        companion object {
            fun getInstance (parent: ViewGroup): CurrencyViewHolder {
                val binding = ItemRecyclerViewBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return CurrencyViewHolder(binding)
            }
        }
    }

    class CurrencyItemCallback : ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }
    }
}