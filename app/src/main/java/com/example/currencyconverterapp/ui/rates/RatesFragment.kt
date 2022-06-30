package com.example.currencyconverterapp.ui.rates

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.databinding.FragmentRatesBinding
import com.example.currencyconverterapp.domain.model.Currency
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RatesFragment : Fragment() {

    private val viewModel: RatesViewModel by viewModels()

    private var _binding: FragmentRatesBinding? = null
    private val binding get() = _binding!!

    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var listAdapter: RatesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getRates(binding.acCurrency.text.toString())

                viewModel.uiState.collect { event ->
                    when (event) {
                        is RatesUiState.Success -> {
                            renderSuccess(event)
                        }
                        is RatesUiState.Loading -> {
                            renderLoading()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun setUpView() {
        spinnerAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, viewModel.getCurrencys())
        binding.acCurrency.apply {
            setAdapter(spinnerAdapter)
            setOnItemClickListener { _, _, _, _ ->
                viewModel.getRates(binding.acCurrency.text.toString())
            }
        }

        listAdapter = RatesAdapter { currency ->
            onClickCurrency(currency)
        }
        binding.recyclerView.adapter = listAdapter
    }

    private fun renderSuccess(event: RatesUiState.Success) {
        binding.progressBar.isVisible = false
        binding.recyclerView.isVisible = true
        listAdapter.submitList(event.items)
    }

    private fun renderLoading() {
        binding.progressBar.isVisible = true
        binding.recyclerView.isVisible = false
    }

    private fun onClickCurrency(currency: Currency) {
        val snackbarText = viewModel.getSelectedRate(
            binding.acCurrency.text.toString(),
            currency
        )
        Snackbar.make(
            binding.root,
            snackbarText,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
