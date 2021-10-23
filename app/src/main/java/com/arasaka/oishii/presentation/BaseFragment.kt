package com.arasaka.oishii.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arasaka.oishii.core.exception.Failure

abstract class BaseFragment (@LayoutRes layoutId: Int): Fragment(layoutId), OnFailure {
    //lazy, generates the variable until the moment it is going to be used, similar to lateinit
    val navController by lazy {findNavController()}
    val baseActivity by lazy {requireActivity() as BaseActivity}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        baseActivity.setUpNavigation(navController);
        setBinding(view);
    }

    abstract fun setBinding(view: View)

    open fun onViewStateChanged(state: BaseViewState?){
        when(state){
            BaseViewState.ShowLoading -> showLoader(true); //Show Throbber refreshing...
            BaseViewState.HideLoading -> showLoader(false);
        }
    }

    open fun showLoader(show:Boolean)= baseActivity.showProgress(show);

    fun showToast(message: String) = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();


    override fun handleFailure(failure: Failure?) {
        //failure type handler (Network, Database & Unauthorized Token)
        when(failure){
            is Failure.DatabaseError -> {

            }
            is Failure.FeatureFailure -> {

            }
            is Failure.NetworkConnection -> {

            }
            is Failure.ServerError -> {
                showToast(failure.serverMessage ?: "Null SERVER error")
            }
            is Failure.Unauthorized -> {

            }
            null -> {

            }
        }
    }



}