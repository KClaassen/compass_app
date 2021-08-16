package com.example.android.netgurucompass.view

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.android.netgurucompass.R
import com.example.android.netgurucompass.databinding.FragmentInsertLocationBinding
import com.example.android.netgurucompass.intent.DataState
import com.example.android.netgurucompass.intent.Intent
import com.example.android.netgurucompass.utils.Localisation
import com.example.android.netgurucompass.viewmodel.InsertLocationViewModel
import com.example.android.netgurucompass.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_insert_location.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class InsertLocationFragment : Fragment() {

    private lateinit var navController: NavController

    private lateinit var binding: FragmentInsertLocationBinding
    private val insertViewModel: InsertLocationViewModel by viewModels()
    private lateinit var localisation: Localisation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_insert_location, container, false)

        initDestinationViewModel()
        initView()

        return binding.root
    }

    private fun initView() {
        btn_setlocation.setOnClickListener {
            try {

                localisation.setLatitude(latitude_txt.text.toString().toDouble())
                localisation.setLongitude(longtitude_txt.text.toString().toDouble())
                //localisation = Navigation.findNavController(it)

                if (navController.currentDestination?.id == R.id.insertLocationFragment) {
                    navController.popBackStack()
                }

            }catch (e:NumberFormatException){
                Toast.makeText(requireContext(),"Insert correct coordinates !", Toast.LENGTH_LONG).show()
            }


        }
    }

    private fun initDestinationViewModel() {
        insertViewModel.initShared(requireContext())
        lifecycleScope.launch {
            insertViewModel.dataState.collect {
                when (it) {
                    is DataState.Success -> {
                        latitude_txt.text = it.coordinates.latitude.toString().toEditable()
                        longtitude_txt.text = it.coordinates.longitude.toString().toEditable()

                    }
                    is DataState.Error -> {
                    }
                    is DataState.Loading -> {
                        println()
                    }
                }
            }
        }

        lifecycleScope.launch {
            insertViewModel.userIntent.send(Intent.GetCoordinates)
        }
    }

    override fun onPause() {
        super.onPause()
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}