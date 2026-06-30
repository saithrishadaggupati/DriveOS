package com.driveos.dashboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.driveos.dashboard.data.model.DTCCode
import com.driveos.dashboard.data.model.VehicleState
import com.driveos.dashboard.data.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: VehicleRepository
) : ViewModel() {

    val vehicleState: StateFlow<VehicleState> = repository.vehicleState
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), VehicleState())

    val activeDTCs: StateFlow<List<DTCCode>> = repository.activeDTCs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun clearDTCs() {
        viewModelScope.launch {
            repository.clearDTCs()
        }
    }
}