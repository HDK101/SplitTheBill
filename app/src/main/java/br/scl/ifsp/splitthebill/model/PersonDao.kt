package br.scl.ifsp.splitthebill.model

import androidx.room.*

@Dao
interface PersonDao {
    @Insert
    fun create(contact: Person)
    @Query("SELECT * FROM Person WHERE id = :id")
    fun retrieve(id: Int): Person?
    @Query("SELECT * FROM Person")
    fun retrieve(): MutableList<Person>
    @Update
    fun update(contact: Person): Int
    @Delete
    fun delete(contact: Person): Int
}