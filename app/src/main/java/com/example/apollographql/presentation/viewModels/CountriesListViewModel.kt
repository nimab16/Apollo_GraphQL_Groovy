package com.example.apollographql.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apollographql.domain.SimpleCountry
import com.example.apollographql.domain.useCases.GetCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CountriesListState(
    val countries: List<SimpleCountry> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class CountriesListViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CountriesListState())
    val state = _state.asStateFlow()

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            _state.update {
                it.copy(
                    countries = getCountriesUseCase.invoke(),
                    isLoading = false
                )
            }
        }
    }
} 