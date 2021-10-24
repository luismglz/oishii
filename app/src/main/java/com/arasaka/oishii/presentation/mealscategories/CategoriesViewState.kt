package com.arasaka.oishii.presentation.mealscategories

import com.arasaka.oishii.domain.model.Category
import com.arasaka.oishii.presentation.BaseViewState

class CategoriesViewState : BaseViewState(){

    data class CategoriesReceived(val categories: List <Category>): BaseViewState()
}