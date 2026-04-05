package com.rma.premiere.ui.screens

import com.rma.premiere.ui.base.BaseViewModel

class FilterMoviesViewModel : BaseViewModel<FilterMoviesState, FilterMoviesEvent>() {

    override fun initialState() = FilterMoviesState()

    override fun onEvent(event: FilterMoviesEvent) {
        when (event) {
            is FilterMoviesEvent.OnQueryChanged -> {
                setState { copy(query = event.query) }
            }

            is FilterMoviesEvent.OnApplyFilters -> {}
            is FilterMoviesEvent.OnClearAll -> {
                setState { copy(query = "") }
            }
        }
    }
}