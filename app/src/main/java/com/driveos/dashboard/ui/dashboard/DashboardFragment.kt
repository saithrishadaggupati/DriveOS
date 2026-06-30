package com.driveos.dashboard.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.driveos.dashboard.R
import com.driveos.dashboard.ui.viewmodel.DashboardViewModel
import com.driveos.dashboard.ui.views.SpeedometerView
import com.driveos.dashboard.ui.views.RPMGaugeView
import com.driveos.dashboard.ui.views.FuelGaugeView
import com.driveos.dashboard.ui.views.TempGaugeView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var speedometer: SpeedometerView
    private lateinit var rpmGauge: RPMGaugeView
    private lateinit var fuelGauge: FuelGaugeView
    private lateinit var tempGauge: TempGaugeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_dashboard, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        speedometer = view.findViewById(R.id.speedometerView)
        rpmGauge = view.findViewById(R.id.rpmGaugeView)
        fuelGauge = view.findViewById(R.id.fuelGaugeView)
        tempGauge = view.findViewById(R.id.tempGaugeView)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.vehicleState.collect { state ->
                speedometer.setSpeed(state.speedKph)
                rpmGauge.setRPM(state.engineRpm)
                fuelGauge.setFuelLevel(state.fuelLevelPercent)
                tempGauge.setTemperature(state.coolantTempC)
            }
        }
    }
}