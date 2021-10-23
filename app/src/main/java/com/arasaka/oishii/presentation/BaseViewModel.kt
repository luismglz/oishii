package com.arasaka.oishii.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arasaka.oishii.core.exception.Failure

abstract class BaseViewModel: ViewModel() {
    //when the values mutate or change, the views will be notified and thus the information will be binded...

    var state: MutableLiveData<BaseViewState> = MutableLiveData();
    var failure: MutableLiveData<Failure> = MutableLiveData();

    protected fun handleFailure(failure: Failure){

        //For modify LiveData value you must specify .value
        this.failure.value = failure;
        state.value = BaseViewState.HideLoading;
    }
}