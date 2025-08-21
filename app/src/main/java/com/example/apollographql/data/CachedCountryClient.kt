package com.example.apollographql.data

import com.example.apollographql.domain.CountryClient
import com.example.apollographql.domain.DetailCountry
import com.example.apollographql.domain.SimpleCountry
import com.example.apollographql.data.local.CountryDao
import com.example.apollographql.data.local.CountryDetailEntity
import com.example.apollographql.data.local.CountryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CachedCountryClient(
    private val remote: CountryClient,
    private val dao: CountryDao
) : CountryClient {

    override suspend fun getCountries(): List<SimpleCountry> = withContext(Dispatchers.IO) {
        // Try network; on success, update cache
        return@withContext try {
            val remoteItems = remote.getCountries()
            dao.upsertCountries(remoteItems.map { SimpleCountry ->
                CountryEntity(
                    code = SimpleCountry.code,
                    name = SimpleCountry.name,
                    capital = SimpleCountry.capital,
                    emoji = SimpleCountry.emoji
                )
            })
            remoteItems
        } catch (e: Exception) {
            // Fallback to cache
            val cached = dao.getCountriesOnce()
            cached.map {
                SimpleCountry(
                    code = it.code,
                    name = it.name,
                    capital = it.capital,
                    emoji = it.emoji
                )
            }
        }
    }

    override suspend fun getCountryDetail(code: String): DetailCountry? = withContext(Dispatchers.IO) {
        // Try network; on success, update cache
        return@withContext try {
            val detail = remote.getCountryDetail(code)
            detail?.let {
                dao.upsertCountryDetail(
                    CountryDetailEntity(
                        code = it.code,
                        name = it.name,
                        capital = it.capital,
                        emoji = it.emoji,
                        continent = it.continent,
                        languages = it.languages,
                        currency = it.currency
                    )
                )
            }
            detail
        } catch (e: Exception) {
            // Fallback to cache
            val cached = dao.getCountryDetailOnce(code)
            cached?.let {
                DetailCountry(
                    code = it.code,
                    name = it.name,
                    capital = it.capital,
                    emoji = it.emoji,
                    continent = it.continent,
                    languages = it.languages,
                    currency = it.currency
                )
            }
        }
    }
}

