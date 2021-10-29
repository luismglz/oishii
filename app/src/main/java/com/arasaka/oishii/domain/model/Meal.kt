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
class Meal(
    @PrimaryKey(autoGenerate = false)
    val idMeal: Int =0,
    @Json(name = "strMeal") val name: String = "",
    @Json(name = "strCategory") val category: String = "",
    @Json(name = "strArea") val area: String = "",
    @Json(name = "strInstructions") val instructions: String = "",
    @Json(name = "strMealThumb") val urlThumb: String = ""
): Parcelable {
    //If large image url its null, take the thumb url value...
    val urlDetailLarge: String
        get() = urlThumb ?: ""
}