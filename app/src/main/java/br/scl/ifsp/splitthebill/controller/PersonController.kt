package br.scl.ifsp.splitthebill.controller

import android.content.Context
import androidx.room.Room
import br.scl.ifsp.splitthebill.SplitTheBillApplication
import br.scl.ifsp.splitthebill.model.Person
import br.scl.ifsp.splitthebill.model.PersonDao
import br.scl.ifsp.splitthebill.model.PersonRoom

class PersonController(context: Context) {
    private val personRoom: PersonRoom
    private val personDao: PersonDao

    init {
        personRoom = Room.databaseBuilder(context, PersonRoom::class.java, PersonRoom.PERSON_DATABASE_FILE).fallbackToDestructiveMigration().build()
        personDao = personRoom.getPersonDao()
    }

    fun list(callback: (person: Person) -> Unit) {
        Thread {
            ca
        }.start()
    }

    fun create(person: Person, callback: (person: Person) -> Unit) {
        Thread {
            personDao.create(person)
            callback(person)
        }.start()
    }

    fun getPersonDao(): PersonDao {
        return personRoom.getPersonDao()
    }
}