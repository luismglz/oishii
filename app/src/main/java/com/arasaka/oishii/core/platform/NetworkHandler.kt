package com.arasaka.oishii.core.platform

import android.content.Context
import com.arasaka.oishii.core.extension.networkInfo
import javax.inject.Inject

//Injection with hilt
class NetworkHandler @Inject constructor(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting == true
}