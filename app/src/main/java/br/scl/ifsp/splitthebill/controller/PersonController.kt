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

    fun list(callback: (person: List<Person>) -> Unit) {
        Thread {
            callback(personDao.retrieve())
        }.start()
    }

    fun create(person: Person, callback: () -> Unit) {
        Thread {
            personDao.create(person)
            callback()
        }.start()
    }

    fun update(person: Person, callback: () -> Unit) {
        Thread {
            personDao.update(person)
            callback()
        }.start()
    }

    fun delete(person: Person, callback: () -> Unit) {
        Thread {
            personDao.delete(person)
            callback()
        }.start()
    }
}