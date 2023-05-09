package br.scl.ifsp.splitthebill

import android.app.Application
import androidx.room.Room
import br.scl.ifsp.splitthebill.model.PersonRoom

class SplitTheBillApplication: Application() {
    //private lateinit var personRoom: PersonRoom

    override fun onCreate() {
        super.onCreate()

        //personRoom = Room.databaseBuilder(this, PersonRoom::class.java, PersonRoom.PERSON_DATABASE_FILE).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

//    fun getPersonRoom(): PersonRoom {
//        //return personRoom
//    }
}