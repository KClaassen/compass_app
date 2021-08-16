package com.example.android.netgurucompass.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.netgurucompass.model.Coordinates
import com.example.android.netgurucompass.viewmodel.MainViewModel

class Localisation(private val context: Context) {


    private var locationManager: LocationManager? = null
    private var viewModel: MainViewModel? = null

    private val PREFERENCES_NAME="SHARED_PREFRENCES"
    private val PREFERENCES_LATITUDE="latitude"
    private val PREFERENCES_LONGITUDE="longitude"

    private val preference=context.getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE)


    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {

            viewModel!!.setCoordinates(Coordinates(latitude =location.latitude,longitude = location.longitude ))

        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    init {

        locationManager =
                context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager?

    }
    /**
     * Mandatory to call , this method active your Location coordinates
     */
    @SuppressLint("MissingPermission")
    open fun setListenerLocationUpdates(viewModel: MainViewModel) {
        this.viewModel = viewModel
        locationManager?.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0L,
                0f,
                locationListener
        )

    }



    fun getLatitude():Double{
        return preference.getString(PREFERENCES_LATITUDE,"0.0")!!.toDouble()
    }

    fun setLatitude(latitude:Double){
        val editor=preference.edit()
        editor.putString(PREFERENCES_LATITUDE,latitude.toString())
        editor.apply()
    }

    fun getLongitude():Double{
        return preference.getString(PREFERENCES_LONGITUDE,"0.0")!!.toDouble()
    }

    fun setLongitude(longitude:Double){
        val editor=preference.edit()
        editor.putString(PREFERENCES_LONGITUDE,longitude.toString())
        editor.apply()
    }
}