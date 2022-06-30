package com.example.currencyconverterapp.ui.rates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.domain.model.Currency

class RatesAdapter(private val onClick: (Currency) -> Unit) :
    ListAdapter<Currency, RatesAdapter.RatesViewHolder>(FlowerDiffCallback) {

    class RatesViewHolder(
        itemView: View,
        val onClick: (Currency) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvRate: TextView = itemView.findViewById(R.id.tvRate)
        private var currentCurrency: Currency? = null

        init {
            itemView.setOnClickListener {
                currentCurrency?.let {
                    onClick(it)
                }
            }
        }

        fun bind(currency: Currency) {
            currentCurrency = currency
            tvName.text = currency.name
            tvRate.text = "${currency.rate}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.currency_item, parent, false)
        return RatesViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        val currency = getItem(position)
        holder.bind(currency)
    }
}

object FlowerDiffCallback : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.name == newItem.name
    }
}
