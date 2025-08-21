package com.example.apollographql.di

import android.content.Context
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.example.apollographql.data.ApolloCountryClient
import com.example.apollographql.data.CachedCountryClient
import com.example.apollographql.data.local.AppDatabase
import com.example.apollographql.data.local.CountryDao
import com.example.apollographql.domain.CountryClient
import com.example.apollographql.domain.useCases.GetCountriesUseCase
import com.example.apollographql.domain.useCases.GetCountryDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://countries.trevorblades.com/graphql")
            .build()
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "countries.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideCountryDao(db: AppDatabase): CountryDao = db.countryDao()

    @Provides
    @Singleton
    fun provideCountryClient(apolloClient: ApolloClient, dao: CountryDao): CountryClient =
        CachedCountryClient(
            remote = ApolloCountryClient(apolloClient),
            dao = dao
        )

    @Provides
    @Singleton
    fun providesGetCountriesUseCase(countryClient: CountryClient): GetCountriesUseCase {
        return GetCountriesUseCase(countryClient)
    }

    @Provides
    @Singleton
    fun providesGetCountryDetailUseCase(countryClient: CountryClient): GetCountryDetailUseCase {
        return GetCountryDetailUseCase(countryClient)
    }
}