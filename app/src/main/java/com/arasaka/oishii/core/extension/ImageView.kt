package com.arasaka.oishii.core.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.arasaka.oishii.R

/*
//Circular rendering image
@BindingAdapter("loadFromURLCircular")
fun ImageView.loadFromURLCircular(url: String) = this.load(url){
    crossfade(true)
    placeholder(R.drawable.ic_cocktail)
    transformations(CircleCropTransformation())
}
*/

//Square rendering
@BindingAdapter("loadFromURL")
fun ImageView.loadFromURL(url: String) = this.load(url){
    crossfade(true)
    placeholder(R.drawable.ic_noodles)
}