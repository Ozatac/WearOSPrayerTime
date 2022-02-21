package com.tunahanozatac.prayertime.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tunahanozatac.prayertime.databinding.ItemCityrowBinding
import com.tunahanozatac.prayertime.model.CityModel
import com.tunahanozatac.prayertime.model.CityModelItem
import com.tunahanozatac.prayertime.util.UserManager
import com.tunahanozatac.prayertime.view.CityFragmentDirections
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    private var newsList: CityModel? = CityModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemCityrowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsList!![position])
    }

    override fun getItemCount(): Int {
        return newsList!!.size
    }

    class ViewHolder(private var binding: ItemCityrowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CityModelItem) {
            binding.name.text = item.SehirAdi
            binding.cardView.setOnClickListener {
                val userManager = UserManager(it.context)
                GlobalScope.launch {
                    userManager.storeUser(item.SehirAdi)
                }
                val action =
                    CityFragmentDirections.actionCityFragmentToDistrictFragment(item.SehirID)
                Navigation.findNavController(binding.root).navigate(action)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(updateNewsList: CityModel, isNew: Int) {
        if (isNew == 1) {
            newsList = updateNewsList
        } else {
            newsList?.addAll(updateNewsList)
        }
        notifyDataSetChanged()
    }
}