package com.rma.premiere.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<State : UiState, Event : UiEvent> : ViewModel() {

    private val _uiState = MutableStateFlow(initialState())
    val uiState: StateFlow<State> = _uiState.asStateFlow()
    abstract fun initialState(): State
    abstract fun onEvent(event: Event)
    protected fun setState(reducer: State.() -> State) {
        _uiState.value = uiState.value.reducer()
    }
}