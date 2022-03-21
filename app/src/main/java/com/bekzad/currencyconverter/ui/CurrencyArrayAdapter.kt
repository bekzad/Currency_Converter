package com.bekzad.currencyconverter.ui

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bekzad.currencyconverter.data.domain.Currency


/**
 * @author Бекзад Насирахунов 20/3/22.
 */
class CurrencyArrayAdapter(context: Context, private val list: List<Currency>)
    : ArrayAdapter<Currency>(context, android.R.layout.simple_spinner_item, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return bindData(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return bindData(position, convertView, parent)
    }

    private fun bindData(position: Int, convertView: View?, parent: ViewGroup ): View {
        val textView = convertView as TextView? ?:
                            LayoutInflater.from(context)
                                .inflate(
                                    android.R.layout.simple_spinner_item,
                                    parent,
                                    false) as TextView

        val currentItem = list[position]
        textView.text = "${currentItem.name} (${currentItem.charCode})"
        return textView
    }

    override fun getDropDownViewTheme(): Resources.Theme? {
        return context.resources.newTheme()
    }
}