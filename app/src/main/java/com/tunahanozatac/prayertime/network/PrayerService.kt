package com.tunahanozatac.prayertime.network

import com.tunahanozatac.prayertime.model.CityModel
import com.tunahanozatac.prayertime.model.DistrictModel
import com.tunahanozatac.prayertime.model.Model
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PrayerService {

    //https://ezanvakti.herokuapp.com/sehirler/2
    //https://ezanvakti.herokuapp.com/ilceler/539
    //https://ezanvakti.herokuapp.com/vakitler/9535

    @GET("sehirler/{id}")
    fun getCity(
        @Path("id") id: Int,
    ): Observable<CityModel>?

    @GET("ilceler/{id}")
    fun getDistrict(
        @Path("id") id: Int,
    ): Observable<DistrictModel>?

    @GET("vakitler/{id}")
    fun getTime(
        @Path("id") id: Int,
    ): Observable<Model>?
}