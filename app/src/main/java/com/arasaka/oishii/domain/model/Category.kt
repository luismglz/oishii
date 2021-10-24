package com.arasaka.oishii.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
@JsonClass(generateAdapter = true)
class Category(
    @PrimaryKey(autoGenerate = false)
    val idCategory: Int =0,
    @Json(name = "strCategory") val nameCategory: String = "",
    @Json(name = "strCategoryDescription") val descriptionCategory: String = "",
    @Json(name = "strCategoryThumb") val urlThumbCategory: String = ""
): Parcelable {
    //If large image url its null, take the thumb url value...
    val descriptionCat: String
        get() = descriptionCategory ?: "Instructions Not Available"
}