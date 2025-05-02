package com.example.apollographql.domain

data class DetailCountry(
    val code: String,
    val emoji: String,
    val name: String,
    val capital: String,
    val currency: String,
    val languages: List<String>,
    val continent: String
)
