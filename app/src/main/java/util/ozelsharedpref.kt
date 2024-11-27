package util

import Roomdb.BesinDatabase

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.prefs.Preferences

class ozelsharedpref {
    companion object {

        private val TIME = "time"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: ozelsharedpref? = null

        private val lock = Any()

        //invoke arkaplanda var çağırılınca ne yapılacağını yazıyor ve bak yoksa yenisimi oluştursun
        operator fun invoke(contex: Context) = instance ?: synchronized(lock) {
            instance ?: ozelsharedprefenecesOlustur(contex).also {
                instance = it
            }
        }

        private fun ozelsharedprefenecesOlustur(contex: Context): ozelsharedpref {

            sharedPreferences=PreferenceManager.getDefaultSharedPreferences(contex)
            return ozelsharedpref()
        }







    }

    fun zamanikaydet(zaman:Long){
        sharedPreferences?.edit()?.putLong(TIME,zaman)?.apply()
    }


    fun zamaniAl()= sharedPreferences?.getLong(TIME,0)



}



