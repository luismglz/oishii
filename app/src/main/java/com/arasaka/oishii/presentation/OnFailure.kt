package com.arasaka.oishii.presentation

import com.arasaka.oishii.core.exception.Failure

interface OnFailure {
    fun handleFailure(failure: Failure?)
}