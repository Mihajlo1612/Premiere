package com.rma.premiere.di

import com.rma.premiere.data.api.MoviesApi
import com.rma.premiere.data.api.MoviesService
import com.rma.premiere.data.api.createHttpClient
import com.rma.premiere.data.api.createMoviesApi
import com.rma.premiere.data.repository.MovieRepository
import com.rma.premiere.ui.screens.FilterMoviesViewModel
import com.rma.premiere.ui.screens.MovieDetailsViewModel
import com.rma.premiere.ui.screens.MoviesListViewModel
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<HttpClient> { createHttpClient() }

    single<Ktorfit> {
        Ktorfit.Builder()
            .baseUrl("https://rma.finlab.rs/")
            .httpClient(get<HttpClient>())
            .build()
    }

    single<MoviesApi> { get<Ktorfit>().createMoviesApi() }

    single<MoviesService> { MoviesService(get<MoviesApi>()) }

    single<MovieRepository> { MovieRepository(get<MoviesService>()) }

    // ViewModel
    viewModel { MoviesListViewModel(get()) }
    viewModel { FilterMoviesViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
}