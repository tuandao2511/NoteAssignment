package com.example.core.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel : ViewModel(){
    val loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun showLoading() {
        loading.value = true
    }

    fun hideLoading() {
        loading.value = false
    }

    fun isLoading(): Boolean {
        return loading.value
    }
}