package com.arasaka.oishii.data.dto

import com.arasaka.oishii.domain.model.Category
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
class CategoriesResponse (val categories: List<Category> ?= listOf())