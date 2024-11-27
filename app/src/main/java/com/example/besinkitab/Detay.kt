package com.example.besinkitab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.besinkitab.databinding.FragmentDetayBinding
import com.example.besinkitab.databinding.FragmentListeBinding
import com.example.besinkitab.viewmodel.BesinDetayViewmodel
import util.gorselIndir
import util.placeHolderYap


class Detay : Fragment() {
    private var _binding: FragmentDetayBinding?=null
    private val binding get() =_binding!!

    private lateinit var viewModel: BesinDetayViewmodel
    var besinId=0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentDetayBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= ViewModelProvider(this)[BesinDetayViewmodel::class.java]

        arguments?.let {
            besinId=DetayArgs.fromBundle(it).besinId
        }

        viewModel.roomVerisiniAl(besinId)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.besinLiveData.observe(viewLifecycleOwner){
            binding.besinSimText.text=it?.besinİsim
            binding.besinKaloriText.text= it?.besinKalori
            binding.besinproteinText.text=it?.besinProtein
            binding.besinkarbonhidratText.text=it?.besinKarbonhidrat
            binding.besinyaText.text=it?.besinYag
            binding.besinMage.gorselIndir(it?.besinGorsel, placeHolderYap(requireContext()))

        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }



}