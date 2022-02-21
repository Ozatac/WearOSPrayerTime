package com.tunahanozatac.prayertime.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tunahanozatac.prayertime.adapter.CityAdapter
import com.tunahanozatac.prayertime.databinding.FragmentCityBinding
import com.tunahanozatac.prayertime.util.UserManager
import com.tunahanozatac.prayertime.viewmodel.PrayerViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class CityFragment : Fragment() {
    private lateinit var viewModel: PrayerViewModel
    private lateinit var _binding: FragmentCityBinding
    private lateinit var cityAdapter: CityAdapter
    private val args: CityFragmentArgs by navArgs()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var userManager: UserManager
    private var isOpenFirst: Boolean = false
    private var isOpenId: String = "9541"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[PrayerViewModel::class.java]
        userManager = UserManager(requireContext())

        val preferences: SharedPreferences =
            requireContext().getSharedPreferences("isOpen", Activity.MODE_PRIVATE)
        isOpenFirst = preferences.getBoolean("isOpen", false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val districtArgs = args.timesToCity
        _binding.progressBar.visibility = View.VISIBLE
        observeData()
        initRecyclerView()
        subSubscribe()
        if (districtArgs) {
            return
        } else {
            if (isOpenFirst) {
                val action =
                    CityFragmentDirections.actionCityFragmentToTimesFragment(isOpenId)
                Navigation.findNavController(view).navigate(action)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun subSubscribe() {
        viewModel.getCityListObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                cityAdapter.updateList(it, 2)
                _binding.progressBar.visibility = View.GONE
                _binding.error.visibility = View.GONE
            } else {
                _binding.error.visibility = View.VISIBLE
                _binding.error.text = "Diyanet Isleri Baskanligi servisine baglanilamiyor."
            }
        }
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(context)
        _binding.cityRecyclerView.layoutManager = layoutManager
        _binding.cityRecyclerView.apply {
            cityAdapter = CityAdapter()
            adapter = cityAdapter
        }
    }

    private fun observeData() {
        userManager.idFLow.asLiveData().observe(viewLifecycleOwner) {
            isOpenId = it
        }
    }
}