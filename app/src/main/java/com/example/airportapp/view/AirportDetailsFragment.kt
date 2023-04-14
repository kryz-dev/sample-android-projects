package com.example.airportapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.airportapp.R
import com.example.airportapp.databinding.AirportDetailsFragmentBinding
import com.example.airportapp.viewmodel.AirportViewModel

class AirportDetailsFragment : Fragment() {
    private var binding: AirportDetailsFragmentBinding? = null
    private val airportViewModel: AirportViewModel by activityViewModels()
    private var webMap: WebView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AirportDetailsFragmentBinding.inflate(inflater, container, false)
        val btnBack = binding?.root?.findViewById<AppCompatButton>(R.id.acbBack)
        webMap = binding?.root?.findViewById<WebView>(R.id.wvMap)
        btnBack?.apply {
            setOnClickListener {
                NavHostFragment.findNavController(this@AirportDetailsFragment).popBackStack()
            }
        }
        webMap?.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = airportViewModel
        }
        val selectedAirport = airportViewModel.selectedAirport.value
        selectedAirport?.let {
            webMap?.loadUrl(
                "https://www.google.com/maps/search/?api=1&query=" +
                        "${selectedAirport.airportName} Airport"
            )
        }
    }

}