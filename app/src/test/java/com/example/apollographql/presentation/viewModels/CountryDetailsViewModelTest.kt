package com.example.apollographql.presentation.viewModels

import com.example.apollographql.MainDispatcherRule
import com.example.apollographql.domain.CountryClient
import com.example.apollographql.domain.DetailCountry
import com.example.apollographql.domain.SimpleCountry
import com.example.apollographql.domain.useCases.GetCountryDetailUseCase
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CountryDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `loadCountryDetails updates state with details`() = runTest {
        val detail = DetailCountry(
            code = "FR",
            name = "France",
            capital = "Paris",
            emoji = "🇫🇷",
            continent = "Europe",
            languages = listOf("French"),
            currency = "EUR"
        )
        val fakeClient = object : CountryClient {
            override suspend fun getCountries(): List<SimpleCountry> = emptyList()
            override suspend fun getCountryDetail(code: String): DetailCountry? = detail
        }
        val useCase = GetCountryDetailUseCase(fakeClient)
        val vm = CountryDetailsViewModel(useCase)

        vm.loadCountryDetails("FR")
        advanceUntilIdle()

        val state = vm.state.value
        assertEquals(false, state.isLoading)
        assertEquals("France", state.country?.name)
        assertEquals("Europe", state.country?.continent)
        assertEquals("🇫🇷", state.country?.emoji)
    }
}


