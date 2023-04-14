package com.example.airportapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airportapp.R
import com.example.airportapp.database.getAirportDatabase
import com.example.airportapp.databinding.AirportListFragmentBinding
import com.example.airportapp.model.Airport
import com.example.airportapp.repository.AirportRepository
import com.example.airportapp.utils.AirportUtil
import com.example.airportapp.viewmodel.AirportViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AirportListFragment : Fragment() {
    private val airportViewModel: AirportViewModel by activityViewModels()
    private lateinit var airportAdapter: AirportAdapter
    private var binding: AirportListFragmentBinding? = null
    private lateinit var repository: AirportRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = AirportListFragmentBinding.inflate(inflater, container, false)
            val airportRecyclerView = binding?.root?.findViewById<RecyclerView>(R.id.rvAirports)
            airportAdapter = AirportAdapter(emptyList(), this::onSelectAirport)
            airportRecyclerView?.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = airportAdapter
            }
            airportViewModel.showLoading()
            initializeRepository()
            repository.getAirportList()
        }
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = airportViewModel
        }
    }

//    override fun onStart() {
//        super.onStart()
//        repository.getAirportList()
//    }

    private fun initializeRepository() {
        repository = AirportRepository(
            getAirportDatabase(requireActivity().application),
            this::onSuccess,
            this::onFailed
        )
        repository.airports.observe(viewLifecycleOwner) {
            airportAdapter.setData(it)
        }
    }

    private fun onSuccess(airportList: List<Airport>) {
        airportViewModel.hideLoading()
        Toast.makeText(requireContext(), getString(R.string.success_msg), Toast.LENGTH_SHORT)
            .show();
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveDataToDatabase(airportList)
        }
    }

    private fun onFailed(error: Throwable) {
        airportViewModel.hideLoading()
        Toast.makeText(requireContext(), getString(R.string.error_msg), Toast.LENGTH_SHORT).show();
    }

    private fun onSelectAirport(airport: Airport) {
        airportViewModel.setSelectedAirport(airport)
        airportViewModel.setSelectedCurrency(
            AirportUtil.getCurrencyByCountryCode(airport.country.countryCode)
        )
        NavHostFragment.findNavController(this).navigate(R.id.airportDetailsFragment)
    }

    class AirportAdapter(
        private var airportList: List<Airport>,
        val onItemClick: ((Airport) -> Unit)
    ) : RecyclerView.Adapter<AirportAdapter.AirportViewHolder>() {
        inner class AirportViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val airportNameTextView: TextView = view.findViewById(R.id.tvAirportName)
            val countryTextView: TextView = view.findViewById(R.id.tvCountry)

            init {
                view.setOnClickListener {
                    onItemClick.invoke(airportList[adapterPosition])
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirportViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.airport_item, parent, false)
            return AirportViewHolder(view)
        }

        override fun getItemCount(): Int {
            return airportList.count()
        }

        override fun onBindViewHolder(holder: AirportViewHolder, position: Int) {
            val airport = airportList[position]
            holder.airportNameTextView.text = airport.airportName
            holder.countryTextView.text = airport.country.countryName
        }

        fun setData(data: List<Airport>) {
            airportList = data
            notifyDataSetChanged()
        }
    }

}