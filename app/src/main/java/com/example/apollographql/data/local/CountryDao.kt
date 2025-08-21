package com.example.apollographql.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    // Countries list
    @Query("SELECT * FROM countries ORDER BY name ASC")
    fun observeCountries(): Flow<List<CountryEntity>>

    @Query("SELECT * FROM countries ORDER BY name ASC")
    suspend fun getCountriesOnce(): List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCountries(items: List<CountryEntity>)

    @Query("DELETE FROM countries")
    suspend fun clearCountries()

    // Country details
    @Query("SELECT * FROM country_details WHERE code = :code LIMIT 1")
    fun observeCountryDetail(code: String): Flow<CountryDetailEntity?>

    @Query("SELECT * FROM country_details WHERE code = :code LIMIT 1")
    suspend fun getCountryDetailOnce(code: String): CountryDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCountryDetail(item: CountryDetailEntity)

    // Click tracking
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCountryClick(click: CountryClickEntity)

    @Query("SELECT code FROM country_clicks")
    fun observeClickedCodes(): Flow<List<String>>

    @Query("SELECT EXISTS(SELECT 1 FROM country_clicks WHERE code = :code)")
    suspend fun isCodeClicked(code: String): Boolean
}

