package com.example.apollographql.domain.useCases

import com.example.apollographql.domain.CountryClient
import com.example.apollographql.domain.SimpleCountry

class GetCountriesUseCase(
    private val countryClient: CountryClient
) {
    suspend fun invoke(): List<SimpleCountry>{
        val result = countryClient.getCountries().sortedBy { it.name }
        println("")
        return result
    }
}