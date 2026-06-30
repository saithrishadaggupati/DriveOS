package com.driveos.dashboard.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.driveos.dashboard.R
import com.driveos.dashboard.ui.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiagnosticsFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var tvDTCList: TextView
    private lateinit var tvDTCCount: TextView
    private lateinit var btnClearDTCs: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_diagnostics, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvDTCList = view.findViewById(R.id.tvDTCList)
        tvDTCCount = view.findViewById(R.id.tvDTCCount)
        btnClearDTCs = view.findViewById(R.id.btnClearDTCs)

        btnClearDTCs.setOnClickListener {
            viewModel.clearDTCs()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.activeDTCs.collect { dtcs ->
                tvDTCCount.text = "Active DTCs: ${dtcs.size}"
                if (dtcs.isEmpty()) {
                    tvDTCList.text = "No active fault codes"
                } else {
                    tvDTCList.text = dtcs.joinToString("\n") {
                        "${it.code} - ${it.description}"
                    }
                }
            }
        }
    }
}