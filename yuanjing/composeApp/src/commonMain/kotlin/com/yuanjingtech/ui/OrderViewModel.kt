package com.yuanjingtech.ui

import androidx.lifecycle.ViewModel
import com.yuanjingtech.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        OrderUiState(
            pickupOptions = listOf(
                "yyyyy"
            )
        )
    )
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()
}