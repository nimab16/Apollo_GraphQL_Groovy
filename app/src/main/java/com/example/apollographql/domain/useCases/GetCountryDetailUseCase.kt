package com.example.apollographql.domain.useCases

import com.example.apollographql.domain.CountryClient
import com.example.apollographql.domain.DetailCountry

class GetCountryDetailUseCase(
    private val countryClient: CountryClient
) {
    suspend fun invoke(code: String): DetailCountry?{
        return countryClient.getCountryDetail(code)
    }
}