package br.scl.ifsp.splitthebill.model

import androidx.room.*

@Dao
interface PersonDao {
    @Insert
    fun create(person: Person)
    @Query("SELECT * FROM Person WHERE id = :id")
    fun retrieve(id: Int): Person?
    @Query("SELECT * FROM Person")
    fun retrieve(): MutableList<Person>
    @Update
    fun update(person: Person): Int
    @Delete
    fun delete(person: Person): Int
}