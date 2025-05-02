package com.example.apollographql.data

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.example.apollographql.CountriesQuery
import com.example.apollographql.CountryDetailQuery
import com.example.apollographql.domain.CountryClient
import com.example.apollographql.domain.DetailCountry
import com.example.apollographql.domain.SimpleCountry

class ApolloCountryClient(
    private val apolloClient: ApolloClient
):CountryClient {
    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient
            .query(CountriesQuery())
            .execute()
            .data
            ?.countries
            ?.mapNotNull { it?.toSimpleCountry() }?: emptyList()
    }

    override suspend fun getCountryDetail(code: String): DetailCountry? {
        Log.d("ApolloCountryClient", "Fetching details for country code: $code")
        
        val response = apolloClient
            .query(CountryDetailQuery(code))
            .execute()
            
        Log.d("ApolloCountryClient", "Response data: ${response.data}")
        Log.d("ApolloCountryClient", "Response errors: ${response.errors}")
        
        return response.data
            ?.country
            ?.toDetailCountry()
            .also { 
                Log.d("ApolloCountryClient", "Mapped country details: $it")
            }
    }
}