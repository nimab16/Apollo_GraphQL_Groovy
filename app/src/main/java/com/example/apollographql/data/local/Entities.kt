package com.example.apollographql.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey val code: String,
    val name: String,
    val capital: String,
    val emoji: String
)

@Entity(tableName = "country_details")
data class CountryDetailEntity(
    @PrimaryKey val code: String,
    val name: String,
    val capital: String,
    val emoji: String,
    val continent: String,
    val languages: List<String>,
    val currency: String
)

class Converters {
    @TypeConverter
    fun fromStringList(list: List<String>?): String = list?.joinToString("||") ?: ""

    @TypeConverter
    fun toStringList(data: String?): List<String> = data?.takeIf { it.isNotEmpty() }?.split("||") ?: emptyList()
}

@Entity(tableName = "country_clicks")
data class CountryClickEntity(
    @PrimaryKey val code: String,
    val clickedAt: Long
)

