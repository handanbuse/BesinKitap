package com.example.besinkitab.viewmodel

import Roomdb.BesinDatabase
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.besinkitab.Besin
import kotlinx.coroutines.launch

class BesinDetayViewmodel(application: Application): AndroidViewModel(application){
    val besinLiveData = MutableLiveData<Besin?>()

    fun roomVerisiniAl(uuid:Int){
        viewModelScope.launch {
            val dao=BesinDatabase.invoke(getApplication()).besinDao()
          val besin=dao.getBesin(uuid)
            if(besin !=null){
                besinLiveData.value=besin

            }

        }

    }
}