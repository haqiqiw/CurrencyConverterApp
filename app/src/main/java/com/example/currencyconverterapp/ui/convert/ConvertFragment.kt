package com.example.currencyconverterapp.ui.convert

import android.graphics.Color
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
import com.example.currencyconverterapp.databinding.FragmentConvertBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConvertFragment : Fragment() {

    private val viewModel: ConvertViewModel by viewModels()

    private var _binding: FragmentConvertBinding? = null
    private val binding get() = _binding!!

    private lateinit var spinnerFromAdapter: ArrayAdapter<String>
    private lateinit var spinnerToAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConvertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { event ->
                    when (event) {
                        is ConvertUiState.Success -> {
                            renderSuccess(event)
                        }
                        is ConvertUiState.Failure -> {
                            renderFailure(event)
                        }
                        is ConvertUiState.Loading -> {
                            renderLoading()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun setUpView() {
        spinnerFromAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, viewModel.getCurrencys())
        binding.acFrom.setAdapter(spinnerFromAdapter)

        spinnerToAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, viewModel.getCurrencys())
        binding.acTo.setAdapter(spinnerToAdapter)

        binding.btnConvert.setOnClickListener {
            val amount = binding.etAmount.text.toString()
            val from = binding.acFrom.text.toString()
            val to = binding.acTo.text.toString()

            viewModel.convertCurrency(
                amount = amount,
                from = from,
                to = to
            )
        }
    }

    private fun renderSuccess(event: ConvertUiState.Success) {
        binding.progressBar.isVisible = false
        binding.tvResult.apply {
            isVisible = true
            setTextColor(Color.BLACK)
            text = event.resultText
        }
    }

    private fun renderFailure(event: ConvertUiState.Failure) {
        binding.progressBar.isVisible = false
        binding.tvResult.apply {
            isVisible = true
            setTextColor(Color.RED)
            text = event.errorText
        }
    }

    private fun renderLoading() {
        binding.progressBar.isVisible = true
        binding.tvResult.isVisible = false
    }
}
