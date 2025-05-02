package com.example.apollographql.di

import com.apollographql.apollo3.ApolloClient
import com.example.apollographql.data.ApolloCountryClient
import com.example.apollographql.domain.CountryClient
import com.example.apollographql.domain.useCases.GetCountriesUseCase
import com.example.apollographql.domain.useCases.GetCountryDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient{
        return ApolloClient.Builder()
            .serverUrl("https://countries.trevorblades.com/graphql")
            .build()
    }

    @Provides
    @Singleton
    fun provideCountryClient(apolloClient: ApolloClient): CountryClient{
        return ApolloCountryClient(apolloClient)
    }

    @Provides
    @Singleton
    fun providesGetCountriesUseCase(countryClient: CountryClient): GetCountriesUseCase{
        return GetCountriesUseCase(countryClient)
    }

    @Provides
    @Singleton
    fun providesGetCountryDetailUseCase(countryClient: CountryClient): GetCountryDetailUseCase{
        return GetCountryDetailUseCase(countryClient)
    }
}