package com.tunahanozatac.prayertime.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tunahanozatac.prayertime.adapter.DistrictAdapter
import com.tunahanozatac.prayertime.databinding.FragmentDistrictBinding
import com.tunahanozatac.prayertime.viewmodel.PrayerViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class DistrictFragment : Fragment() {

    private lateinit var _binding: FragmentDistrictBinding
    private val args: DistrictFragmentArgs by navArgs()
    private lateinit var viewModel: PrayerViewModel
    private lateinit var districtAdapter: DistrictAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[PrayerViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDistrictBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cityId = args.cityId
        viewModel.makeApiCallDistrict(cityId.toInt())
        _binding.progressBar.visibility = View.VISIBLE
        initRecyclerView()
        subSubscribe()
    }

    private fun subSubscribe() {
        viewModel.getDistrictListObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                districtAdapter.updateList(it, 2)
                _binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(context)
        _binding.districtRecyclerView.layoutManager = layoutManager
        _binding.districtRecyclerView.apply {
            districtAdapter = DistrictAdapter()
            adapter = districtAdapter
        }
    }
}