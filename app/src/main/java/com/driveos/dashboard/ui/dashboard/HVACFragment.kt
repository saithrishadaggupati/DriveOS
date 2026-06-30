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
class HVACFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var tvCabinTemp: TextView
    private lateinit var tvFanSpeed: TextView
    private lateinit var tvACStatus: TextView
    private lateinit var tvTargetTemp: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_hvac, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvCabinTemp = view.findViewById(R.id.tvCabinTemp)
        tvFanSpeed = view.findViewById(R.id.tvFanSpeed)
        tvACStatus = view.findViewById(R.id.tvACStatus)
        tvTargetTemp = view.findViewById(R.id.tvTargetTemp)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.vehicleState.collect { state ->
                tvCabinTemp.text = "${state.cabinTempC}°C"
                tvFanSpeed.text = "Fan: ${state.fanSpeed}"
                tvACStatus.text = if (state.acOn) "AC: ON" else "AC: OFF"
                tvTargetTemp.text = "Target: ${state.targetTempC}°C"
            }
        }
    }
}