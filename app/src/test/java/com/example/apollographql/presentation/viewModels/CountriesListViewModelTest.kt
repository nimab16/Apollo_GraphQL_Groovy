package com.example.apollographql.presentation.viewModels

import com.example.apollographql.MainDispatcherRule
import com.example.apollographql.domain.CountryClient
import com.example.apollographql.domain.DetailCountry
import com.example.apollographql.domain.SimpleCountry
import com.example.apollographql.domain.useCases.GetCountriesUseCase
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CountriesListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `loadCountries updates state with countries`() = runTest {
        val countries = listOf(
            SimpleCountry(code = "US", name = "United States", capital = "Washington", emoji = "🇺🇸"),
            SimpleCountry(code = "FR", name = "France", capital = "Paris", emoji = "🇫🇷")
        )
        val fakeClient = object : CountryClient {
            override suspend fun getCountries(): List<SimpleCountry> = countries
            override suspend fun getCountryDetail(code: String): DetailCountry? = null
        }
        val useCase = GetCountriesUseCase(fakeClient)
        val vm = CountriesListViewModel(useCase)

        advanceUntilIdle()

        val state = vm.state.value
        assertEquals(false, state.isLoading)
        assertEquals(2, state.countries.size)
        // Use case sorts by name, so France should be first
        assertEquals("France", state.countries.first().name)
    }
}
