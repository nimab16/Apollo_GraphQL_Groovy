package com.example.apollographql.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.apollographql.R
import com.example.apollographql.databinding.FragmentCountryDetailsBinding
import com.example.apollographql.presentation.viewModels.CountryDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryDetailsFragment : Fragment() {

    private var _binding: FragmentCountryDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CountryDetailsViewModel by viewModels()
    private val args: CountryDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadCountryDetails(args.countryCode)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                state.country?.let { country ->
                    binding.tvCountryName.text = country.name
                    binding.tvCountryCode.text = getString(R.string.country_code, country.code)
                    binding.tvCountryContinent.text =
                        getString(R.string.continent, country.continent)
                    binding.tvCountryCapital.text = getString(R.string.capital, country.capital)
                    binding.tvCountryCurrency.text = getString(R.string.currency, country.currency)
                    binding.tvCountryLanguages.text =
                        getString(R.string.languages, country.languages.joinToString(", "))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 