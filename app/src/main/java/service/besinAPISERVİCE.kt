package service

import com.example.besinkitab.Besin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class besinAPISERVİCE {

    //retrofiti şimdilik çağırma
   private val retrofit= Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create()) // verinin ne tipte olacağını
        .build()
        .create(besinAPI::class.java) // servis sınıfnı soruyor


    //verileri almak için

    suspend fun getData(): List<Besin>{
        return retrofit.getBesin()
    }

}