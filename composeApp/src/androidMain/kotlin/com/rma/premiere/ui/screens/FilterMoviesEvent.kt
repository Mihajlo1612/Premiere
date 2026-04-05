package com.rma.premiere.ui.screens

import com.rma.premiere.ui.base.UiEvent

sealed class FilterMoviesEvent : UiEvent {
    data class OnQueryChanged(val query: String) : FilterMoviesEvent()
    object OnApplyFilters : FilterMoviesEvent()
    object OnClearAll : FilterMoviesEvent()
}