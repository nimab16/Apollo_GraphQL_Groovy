package com.example.apollographql.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apollographql.R
import com.example.apollographql.databinding.ItemCountryBinding
import com.example.apollographql.domain.SimpleCountry
import androidx.core.graphics.toColorInt

class CountriesAdapter(
    private val isClicked: (String) -> Boolean,
    private val onCountryClick: (SimpleCountry) -> Unit
) : ListAdapter<SimpleCountry, CountriesAdapter.CountryViewHolder>(CountryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountryViewHolder(binding, isClicked, onCountryClick)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CountryViewHolder(
        private val binding: ItemCountryBinding,
        private val isClicked: (String) -> Boolean,
        private val onCountryClick: (SimpleCountry) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(country: SimpleCountry) {
            binding.tvCountryName.text = country.name
            binding.tvCountryCapital.text =
                binding.root.context.getString(R.string.capital_icon, country.capital)
            binding.tvCountryEmoji.text = country.emoji

            // Apply background color based on persisted state
            val clicked = isClicked(country.code)
            binding.cardContainer.setCardBackgroundColor(
                if (clicked) itemView.context.getString(R.string.grey).toColorInt() else Color.WHITE
            )

            binding.root.setOnClickListener {
                binding.cardContainer.setCardBackgroundColor(itemView.context.getString(R.string.grey).toColorInt())
                onCountryClick(country)
            }
        }
    }

    private class CountryDiffCallback : DiffUtil.ItemCallback<SimpleCountry>() {
        override fun areItemsTheSame(oldItem: SimpleCountry, newItem: SimpleCountry): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: SimpleCountry, newItem: SimpleCountry): Boolean {
            return oldItem == newItem
        }
    }
} 