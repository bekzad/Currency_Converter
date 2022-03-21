package com.bekzad.currencyconverter.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bekzad.currencyconverter.Injection
import com.bekzad.currencyconverter.databinding.FragmentMainBinding
import com.bekzad.currencyconverter.utils.hideOnEnterListener
import com.bekzad.currencyconverter.utils.showToast


/**
 * @author Бекзад Насирахунов 19/3/22.
 */
class MainFragment : Fragment() {
    private lateinit var vb: FragmentMainBinding

    private val vm: MainViewModel by activityViewModels() {
        Injection.provideViewModelFactory(requireActivity())
    }

    private val arrayAdapter: CurrencyArrayAdapter by lazy {
        CurrencyArrayAdapter(requireActivity(), mutableListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentMainBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRefreshing()
        setupCurrencySelector()
        setupEditText()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        val recyclerViewAdapter = CurrencyRVAdapter()
        vb.recyclerView.adapter = recyclerViewAdapter

        vm.currencies.observe(viewLifecycleOwner) { currencies ->
            currencies?.let {
                recyclerViewAdapter.submitList(it)
                arrayAdapter.clear()
                arrayAdapter.addAll(it)
            }
        }

        vm.resultText.observe(viewLifecycleOwner) { text ->
            text?.let { vb.resultTv.text = it }
        }
    }

    private fun setupRefreshing() {
        vb.swipeRefresh.setOnRefreshListener {
            vm.refreshUI()
        }
        var toastCount = 0
        vm.status.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (it) {
                    Status.LOADING -> vb.swipeRefresh.isRefreshing = true
                    Status.DONE -> vb.swipeRefresh.isRefreshing = false
                    Status.ERROR -> {
                        vb.swipeRefresh.isRefreshing = false
                        if (toastCount == 0) {
                            requireActivity().showToast("Check out your internet connection")
                            toastCount++
                        }
                    }
                }
            }
        }
    }

    private fun setupCurrencySelector() {
        vb.spinner.adapter = arrayAdapter

        vb.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int,
                id: Long
            ) {
                vm.updateSelectedCurrency(arrayAdapter.getItem(position)!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>) { /* pass */ }
        }
    }

    private fun setupEditText() {
        vb.countEt.setOnKeyListener(hideOnEnterListener(requireActivity()))

        vb.countEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                vm.updateCount(s?.toString())
            }
        })
    }
}