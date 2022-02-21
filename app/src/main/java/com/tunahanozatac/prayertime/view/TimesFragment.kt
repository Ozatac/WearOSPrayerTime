package com.tunahanozatac.prayertime.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.tunahanozatac.prayertime.databinding.FragmentTimesBinding
import com.tunahanozatac.prayertime.util.UserManager
import com.tunahanozatac.prayertime.viewmodel.PrayerViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@DelicateCoroutinesApi
class TimesFragment : Fragment() {

    private lateinit var binding: FragmentTimesBinding
    private val args: TimesFragmentArgs by navArgs()
    private lateinit var viewModel: PrayerViewModel
    private lateinit var userManager: UserManager
    private var cityTextName: String = ""
    private var districtTextName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[PrayerViewModel::class.java]
        userManager = UserManager(requireContext())

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        val editor: SharedPreferences.Editor =
            requireContext().getSharedPreferences("isOpen", Context.MODE_PRIVATE).edit()
        editor.putBoolean("isOpen", true)
        editor.apply()

        val districtArgs = args.districtId
        GlobalScope.launch {
            userManager.id(districtArgs)
        }

        userManager.userNameFlow.asLiveData().observe(viewLifecycleOwner) {
            cityTextName = it
        }

        userManager.districtNameFlow.asLiveData().observe(viewLifecycleOwner) {
            districtTextName = it
            setName()
        }

        viewModel.makeApiCallTimes(districtArgs.toInt())
        subSubscribe()

        binding.cityText.setOnClickListener {
            val action =
                TimesFragmentDirections.actionTimesFragmentToCityFragment(true)
            Navigation.findNavController(view).navigate(action)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun subSubscribe() {
        viewModel.getTimesListObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.progressBar.visibility = View.GONE
                binding.imsakName.text = it[0].Imsak
                binding.calander.text = it[0].MiladiTarihUzun
                binding.gunesName.text = it[0].Gunes
                binding.ogleName.text = it[0].Ogle
                binding.ikindiName.text = it[0].Ikindi
                binding.aksamName.text = it[0].Aksam
                binding.yatsiName.text = it[0].Yatsi
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setName() {
        if (cityTextName.isNotEmpty() && districtTextName.isNotEmpty()) {
            binding.cityText.text = "$cityTextName/ $districtTextName"
        }
    }
}