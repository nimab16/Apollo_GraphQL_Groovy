package com.example.apollographql.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apollographql.data.local.CountryClickEntity
import com.example.apollographql.data.local.CountryDao
import com.example.apollographql.databinding.FragmentCountriesListBinding
import com.example.apollographql.presentation.adapters.CountriesAdapter
import com.example.apollographql.presentation.viewModels.CountriesListViewModel
import com.example.apollographql.domain.SimpleCountry
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountriesListFragment : Fragment() {

    private var _binding: FragmentCountriesListBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var countryDao: CountryDao

    private val viewModel: CountriesListViewModel by viewModels()
    private lateinit var adapter: CountriesAdapter

    private val clickedSet: MutableSet<String> = linkedSetOf()

    private var allCountries: List<SimpleCountry> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountriesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CountriesAdapter(
            isClicked = { code -> clickedSet.contains(code) },
            onCountryClick = { country ->
                // Persist click in Room
                viewLifecycleOwner.lifecycleScope.launch {
                    countryDao.upsertCountryClick(
                        CountryClickEntity(
                            code = country.code,
                            clickedAt = System.currentTimeMillis()
                        )
                    )
                }
                findNavController().navigate(
                    CountriesListFragmentDirections.actionCountriesListFragmentToCountryDetailsFragment(country.code)
                )
            }
        )
        setupRecyclerView()
        setupSearch()
        observeViewModel()
        observeClickedCodes()
    }

    private fun setupRecyclerView() {
        binding.rvCountries.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CountriesListFragment.adapter
        }
    }

    private fun setupSearch() {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            filterAndSubmit(text?.toString().orEmpty())
        }
    }

    private fun filterAndSubmit(query: String) {
        val filtered = if (query.isBlank()) allCountries
        else allCountries.filter { it.name.contains(query, ignoreCase = true) }
        adapter.submitList(filtered)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                allCountries = state.countries
                filterAndSubmit(binding.etSearch.text?.toString().orEmpty())
            }
        }
    }

    private fun observeClickedCodes() {
        viewLifecycleOwner.lifecycleScope.launch {
            countryDao.observeClickedCodes().collect { codes ->
                clickedSet.clear()
                clickedSet.addAll(codes)
                filterAndSubmit(binding.etSearch.text?.toString().orEmpty())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 