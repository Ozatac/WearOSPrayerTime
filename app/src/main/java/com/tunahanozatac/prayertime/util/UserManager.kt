package com.tunahanozatac.prayertime.util

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {
    private val dataStore = context.createDataStore(name = "user_prefs")

    companion object {
        val USER_ID = preferencesKey<String>("USER_ID")
        val CITY_NAME = preferencesKey<String>("USER_NAME")
        val DISTRICT_NAME = preferencesKey<String>("DISTRICT_NAME")
        val FIRST_OPEN = preferencesKey<Boolean>("FIRST_OPEN")
    }

    suspend fun id(userId: String) {
        dataStore.edit {
            it[USER_ID] = userId
        }
    }

    suspend fun storeUser(name: String) {
        dataStore.edit {
            it[CITY_NAME] = name
        }
    }

    suspend fun storeDistrict(district: String) {
        dataStore.edit {
            it[DISTRICT_NAME] = district
        }
    }

    suspend fun storeIsOpen(isOpen: Boolean) {
        dataStore.edit {
            it[FIRST_OPEN] = isOpen
        }
    }

    val idFLow: Flow<String> = dataStore.data.map {
        it[USER_ID] ?: ""
    }

    val userNameFlow: Flow<String> = dataStore.data.map {
        it[CITY_NAME] ?: ""
    }

    val districtNameFlow: Flow<String> = dataStore.data.map {
        it[DISTRICT_NAME] ?: ""
    }

    val isOpenFlow: Flow<Boolean> = dataStore.data.map {
        it[FIRST_OPEN] ?: false
    }
}