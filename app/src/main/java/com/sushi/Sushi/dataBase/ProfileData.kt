package com.sushi.Sushi.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ProfileData {
    @PrimaryKey
    var id: Long = 0
    var name: String? = null
    var phone: String? = null
    var birthdate: String? = null

}