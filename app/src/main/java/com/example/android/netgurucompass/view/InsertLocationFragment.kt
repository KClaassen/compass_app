package com.example.android.netgurucompass.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.android.netgurucompass.R
import com.example.android.netgurucompass.databinding.FragmentInsertLocationBinding

class InsertLocationFragment : Fragment() {

    private lateinit var binding: FragmentInsertLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_insert_location, container, false)


        return binding.root
    }
}