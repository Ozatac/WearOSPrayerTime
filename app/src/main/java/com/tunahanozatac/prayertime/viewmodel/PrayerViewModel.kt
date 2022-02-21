package com.tunahanozatac.prayertime.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tunahanozatac.prayertime.model.CityModel
import com.tunahanozatac.prayertime.model.DistrictModel
import com.tunahanozatac.prayertime.model.Model
import com.tunahanozatac.prayertime.network.RetrofitInstance
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PrayerViewModel : ViewModel() {

    var loading: MutableLiveData<Boolean> = MutableLiveData()
    private var timesList: MutableLiveData<Model?> = MutableLiveData()
    private var cityList: MutableLiveData<CityModel?> = MutableLiveData()
    private var districtList: MutableLiveData<DistrictModel?> = MutableLiveData()

    init {
        makeApiCallCity(2)
    }

    private fun makeApiCallCity(id: Int) {
        val retrofitInstance = RetrofitInstance.getRetrofit()
        retrofitInstance!!.getCity(id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(getCityListObserverRx())
    }

    private fun getCityListObserverRx(): Observer<CityModel> {
        return object : Observer<CityModel> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: CityModel) {
                cityList.value = t
                loading.value = true
            }

            override fun onError(e: Throwable) {
                cityList.value = null
                loading.value = false
            }

            override fun onComplete() {}
        }
    }

    fun getCityListObserver(): MutableLiveData<CityModel?> {
        return cityList
    }

    fun makeApiCallDistrict(id: Int) {
        val retrofitInstance = RetrofitInstance.getRetrofit()
        retrofitInstance!!.getDistrict(id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(getDistrictListObserverRx())
    }

    private fun getDistrictListObserverRx(): Observer<DistrictModel> {
        return object : Observer<DistrictModel> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: DistrictModel) {
                districtList.value = t
                loading.value = true
            }

            override fun onError(e: Throwable) {
                districtList.value = null
                loading.value = false
            }

            override fun onComplete() {}
        }
    }

    fun getDistrictListObserver(): MutableLiveData<DistrictModel?> {
        return districtList
    }

    fun makeApiCallTimes(id: Int) {
        val retrofitInstance = RetrofitInstance.getRetrofit()
        retrofitInstance!!.getTime(id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(getTimesListObserverRx())
    }

    private fun getTimesListObserverRx(): Observer<Model> {
        return object : Observer<Model> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: Model) {
                timesList.value = t
                loading.value = true
            }

            override fun onError(e: Throwable) {
                timesList.value = null
                loading.value = false
            }

            override fun onComplete() {}
        }
    }

    fun getTimesListObserver(): MutableLiveData<Model?> {
        return timesList
    }
}