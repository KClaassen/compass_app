package com.example.android.netgurucompass.view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.netgurucompass.R
import com.example.android.netgurucompass.databinding.FragmentMainBinding
import com.example.android.netgurucompass.utils.Localisation
import com.example.android.netgurucompass.viewmodel.MainViewModel
import java.util.jar.Manifest

private const val PERMISSION_REQUEST = 0

class MainFragment : Fragment() {

    private var permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var localisation: Localisation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.btnSetNextDestination.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_insertLocationFragment)
        }

        return binding.root
    }


    private fun initLocalization() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(permissions)) {
                localisation!!.setListenerLocationUpdates(viewModel)
                var gps_enabled = false
                try {
                    var lm: LocationManager =
                        requireActivity().getSystemService(Context.LOCATION_SERVICE) as (LocationManager)
                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                if (!gps_enabled) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            } else {
                requestPermissions(permissions, PERMISSION_REQUEST)
                //showView()
            }
        }
    }

    private fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (context?.let {
                    PermissionChecker.checkCallingOrSelfPermission(
                        it,
                        permissionArray[i]
                    )
                } == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain =
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                            permissions[i]
                        )
                    if (requestAgain) {
                        Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            activity,
                            "Go to settings and enable the permission",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            if (allSuccess) {

                localisation!!.setListenerLocationUpdates(viewModel)
                var gps_enabled = false
                try {
                    var lm: LocationManager =
                        requireActivity().getSystemService(Context.LOCATION_SERVICE) as (LocationManager)
                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                if (!gps_enabled) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }

                localisation!!.setListenerLocationUpdates(viewModel)
            }


        }
    }
}