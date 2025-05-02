package com.example.apollographql.data

import com.example.apollographql.CountriesQuery
import com.example.apollographql.CountryDetailQuery
import com.example.apollographql.domain.DetailCountry
import com.example.apollographql.domain.SimpleCountry

fun CountryDetailQuery.Country.toDetailCountry(): DetailCountry {
    return DetailCountry(
        code = code ?: "",
        emoji = emoji ?: "",
        capital = capital ?: "No Capital",
        languages = languages?.mapNotNull { it?.name } ?: emptyList(),
        continent = continent?.name ?: "No Continent",
        name = name ?: "No name",
        currency = currency ?: "No Currency"
    )
}

fun CountriesQuery.Country.toSimpleCountry(): SimpleCountry {
    return SimpleCountry(
        code = code ?: "",
        emoji = emoji ?: "",
        capital = capital ?: "No Capital",
        name = name ?: "No name"
    )
}

