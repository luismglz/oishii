package com.arasaka.oishii.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
class User (
    @PrimaryKey(autoGenerate = true)
    val idUser: Int =0,
    val username: String = "",
    val mail: String = "",
    val avatar: String = "",
    val token: String? = ""
) {
}