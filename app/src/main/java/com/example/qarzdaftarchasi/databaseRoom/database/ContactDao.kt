package com.example.qarzdaftarchasi.databaseRoom.database

import androidx.room.*
import com.example.qarzdaftarchasi.databaseRoom.entities.Contact

@Dao
interface ContactDao {
    @Insert
    fun addContact(contact: Contact)

    @Insert
    fun addContactList(list: List<Contact>)

    @Insert
    fun addContacts(vararg contact: Contact)

    @Update
    fun editContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("select * from contact")
    fun getAllContacts():List<Contact>

    @Query("select * from contact where id=:id")
    fun getContactById(id:Int): Contact

    @Query("select * from contact where name like '%' || :name || '%'")
    fun getContactFilter(name:String):List<Contact>
}