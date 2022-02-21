package com.tunahanozatac.prayertime.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tunahanozatac.prayertime.databinding.ItemCityrowBinding
import com.tunahanozatac.prayertime.model.DistrictModel
import com.tunahanozatac.prayertime.model.DistrictModelItem
import com.tunahanozatac.prayertime.util.UserManager
import com.tunahanozatac.prayertime.view.DistrictFragmentDirections
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class DistrictAdapter : RecyclerView.Adapter<DistrictAdapter.ViewHolder>() {

    private var newsList: DistrictModel? = DistrictModel()

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

        fun bind(item: DistrictModelItem) {
            binding.name.text = item.IlceAdi
            binding.cardView.setOnClickListener {
                val userManager = UserManager(it.context)
                GlobalScope.launch {
                    userManager.storeDistrict(item.IlceAdi)
                }
                val action =
                    DistrictFragmentDirections.actionDistrictFragmentToTimesFragment(item.IlceID)
                Navigation.findNavController(binding.root).navigate(action)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(updateNewsList: DistrictModel, isNew: Int) {
        if (isNew == 1) {
            newsList = updateNewsList
        } else {
            newsList?.addAll(updateNewsList)
        }
        notifyDataSetChanged()
    }
}