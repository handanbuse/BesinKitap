package service

import com.example.besinkitab.Besin
import retrofit2.http.GET

interface besinAPI {
    // https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/refs/heads/master/besinler.json

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/refs/heads/master/besinler.json")
    suspend fun  getBesin():List<Besin>

}