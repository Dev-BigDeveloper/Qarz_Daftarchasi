package com.example.qarzdaftarchasi.databaseRoom.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var number:String,
    var image:String
)
