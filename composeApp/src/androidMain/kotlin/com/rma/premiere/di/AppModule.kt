package com.rma.premiere.di

import com.rma.premiere.data.api.MoviesService
import com.rma.premiere.data.api.createHttpClient
import com.rma.premiere.data.repository.MovieRepository
import com.rma.premiere.ui.screens.FilterMoviesViewModel
import com.rma.premiere.ui.screens.MoviesListViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // HttpClient
    single<HttpClient> { createHttpClient() }

    // MoviesService
    single { MoviesService(get()) }

    // Repository
    single { MovieRepository(get()) }

    // ViewModel
    viewModel { MoviesListViewModel(get()) }

    viewModel { FilterMoviesViewModel(get()) }
}