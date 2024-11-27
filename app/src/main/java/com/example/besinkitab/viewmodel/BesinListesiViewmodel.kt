package com.example.besinkitab.viewmodel

import Roomdb.BesinDatabase
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.besinkitab.Besin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import service.besinAPISERVİCE
import util.ozelsharedpref

class BesinListesiViewmodel(application:Application):AndroidViewModel(application) {


    val besinler=MutableLiveData<List<Besin>>()
    val besinHataMesaji=MutableLiveData<Boolean>()
    val besinYukleniyor=MutableLiveData<Boolean>() //progresbarı gösterecek false olursa göstermeyecek




    private val besinApiservis = besinAPISERVİCE()
    private val ozelsharedpref=ozelsharedpref(getApplication())

    private val guncellemezamani=10 * 60* 1000* 1000* 1000L

    fun refreshData(){
        val kaydedilmezamani = ozelsharedpref.zamaniAl()
        if (kaydedilmezamani !=null && kaydedilmezamani !=0L && System.nanoTime()-kaydedilmezamani < guncellemezamani){
            //roomdan verileri alıcaz
            verileriRoomdanAl()
        }else{
            verileriInternttencek()
        }
    }

    fun refreshDataInternet(){
        verileriInternttencek()
    }
    private fun verileriRoomdanAl(){
        besinYukleniyor.value=true
        viewModelScope.launch(Dispatchers.IO) {
            val besinList=BesinDatabase(getApplication()).besinDao().getAllBesin()
            withContext(Dispatchers.Main){
                besinlerigoster(besinList)
                Toast.makeText(getApplication(),"besinleri roomdan aldık",Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun verileriInternttencek(){

        besinYukleniyor.value=true



        viewModelScope.launch (Dispatchers.IO){
            val besinlerListesi= besinApiservis.getData()

            //besin yüklendikten snra false yapma
            withContext(Dispatchers.Main){
                besinYukleniyor.value=false
                roomkaydet(besinlerListesi)
                Toast.makeText(getApplication(),"Besinler İnternetten aldık",Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun besinlerigoster(besinList: List<Besin>){
        besinler.value=besinList
        besinHataMesaji.value=false
        besinYukleniyor.value=false
    }


    //Sqlite kaydetme fonksiyonu

    private fun roomkaydet(besinList: List<Besin>){
        viewModelScope.launch {
             val dao=BesinDatabase(getApplication()).besinDao()
            //veri gelrse
            dao.deleteAllBesin()
             val uuidListesi=dao.insertAll(*besinList.toTypedArray()) // tek tek verecek
            var i=0
            while (i<besinList.size){
                besinList[1].uuid=uuidListesi[i].toInt()
                i=i+1
            }

            besinlerigoster(besinList)

        }
        ozelsharedpref.zamanikaydet(System.nanoTime())
    }
}