package com.example.apollographql.domain

interface CountryClient {
    suspend fun getCountries(): List<SimpleCountry>
    suspend fun getCountryDetail(code:String): DetailCountry?
}