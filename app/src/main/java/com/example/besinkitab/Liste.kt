package com.example.besinkitab

import adapter.BesinRecyclerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.besinkitab.databinding.FragmentListeBinding
import com.example.besinkitab.viewmodel.BesinListesiViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import service.besinAPI

class Liste : Fragment() {

private var _binding:FragmentListeBinding?=null
    private val binding get() =_binding!!

    private lateinit var viewModel:BesinListesiViewmodel
    private val recycleradapter=BesinRecyclerAdapter(arrayListOf())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentListeBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this)[BesinListesiViewmodel::class.java] //  erişim sağlanır
        viewModel.refreshData()

        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = recycleradapter



        binding.refreshswipe.setOnRefreshListener {
            binding.recyclerView.visibility=View.GONE
            binding.besinhatamsj.visibility=View.GONE
            binding.refreshswipe.isRefreshing =false

        }
        observeLiveData()






     }
    private fun observeLiveData(){
        viewModel.besinler.observe(viewLifecycleOwner){
            //adapter
            recycleradapter.besinListesiguncelle(it)
            binding.recyclerView.visibility =View.VISIBLE
        }

        viewModel.besinHataMesaji.observe(viewLifecycleOwner){
            if(it){
                binding.besinhatamsj.visibility=View.VISIBLE
                binding.recyclerView.visibility=View.GONE

            }else{
                binding.besinhatamsj.visibility=View.GONE
            }
        }


        viewModel.besinYukleniyor.observe(viewLifecycleOwner){
            if(it){
                binding.besinhatamsj.visibility=View.GONE
                binding.recyclerView.visibility=View.GONE

            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}