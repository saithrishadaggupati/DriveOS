package com.driveos.dashboard.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.driveos.dashboard.R
import com.driveos.dashboard.ui.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TripComputerFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var tvTripDistance: TextView
    private lateinit var tvAvgSpeed: TextView
    private lateinit var tvFuelConsumed: TextView
    private lateinit var tvAvgFuelEconomy: TextView
    private lateinit var tvTripDuration: TextView
    private lateinit var tvOdometer: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_trip, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvTripDistance = view.findViewById(R.id.tvTripDistance)
        tvAvgSpeed = view.findViewById(R.id.tvAvgSpeed)
        tvFuelConsumed = view.findViewById(R.id.tvFuelConsumed)
        tvAvgFuelEconomy = view.findViewById(R.id.tvAvgFuelEconomy)
        tvTripDuration = view.findViewById(R.id.tvTripDuration)
        tvOdometer = view.findViewById(R.id.tvOdometer)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.vehicleState.collect { state ->
                tvTripDistance.text = "Trip: ${"%.1f".format(state.tripDistanceKm)} km"
                tvAvgSpeed.text = "Avg Speed: ${"%.1f".format(state.avgSpeedKph)} km/h"
                tvFuelConsumed.text = "Fuel Used: ${"%.2f".format(state.fuelConsumedL)} L"
                tvAvgFuelEconomy.text = "Economy: ${"%.1f".format(state.fuelEconomyL100km)} L/100km"
                tvTripDuration.text = "Duration: ${formatDuration(state.tripDurationSeconds)}"
                tvOdometer.text = "ODO: ${state.odometerKm} km"
            }
        }
    }

    private fun formatDuration(seconds: Long): String {
        val h = seconds / 3600
        val m = (seconds % 3600) / 60
        val s = seconds % 60
        return "%02d:%02d:%02d".format(h, m, s)
    }
}