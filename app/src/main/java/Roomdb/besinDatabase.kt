package Roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.besinkitab.Besin

@Database(entities = [Besin::class],version=1)
        abstract class BesinDatabase: RoomDatabase(){
            abstract fun besinDao(): BesinDAO




            //tek oluşturduğu nesneyi tekrar oluşturup kullanıyor.
            companion object{
                @Volatile
                private var instance:BesinDatabase?= null

                private val lock= Any()

                //invoke arkaplanda var çağırılınca ne yapılacağını yazıyor ve bak yoksa yenisimi oluştursun
                operator  fun invoke(contex:Context)= instance?: synchronized(lock){
                    instance ?: databaseOlustur(contex).also {
                        instance=it
                    }
                }

                private fun databaseOlustur(contex: Context)=Room.databaseBuilder(
                    contex.applicationContext,
                    BesinDatabase::class.java,
                    "besinDatabase"
                ).build()

            }
        }


