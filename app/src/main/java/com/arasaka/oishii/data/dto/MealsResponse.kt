package com.arasaka.oishii.data.dto

import com.arasaka.oishii.domain.model.Meal
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
class MealsResponse (val meals: List<Meal> ?= listOf())//If the list response is null, this 'return' an empty list...