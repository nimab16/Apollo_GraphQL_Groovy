package com.example.apollographql.presentation.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apollographql.domain.DetailCountry
import com.example.apollographql.domain.useCases.GetCountryDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CountryDetailsState(
    val country: DetailCountry? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val getCountryDetailUseCase: GetCountryDetailUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CountryDetailsState())
    val state = _state.asStateFlow()

    fun loadCountryDetails(code: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            
            val countryDetails = getCountryDetailUseCase.invoke(code)
            
            _state.update {
                it.copy(
                    country = countryDetails,
                    isLoading = false
                )
            }
        }
    }
} 