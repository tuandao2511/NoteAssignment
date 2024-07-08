package com.example.core.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.core.ui.view.LoadingDialog

abstract class BaseActivity<VB: ViewBinding> : AppCompatActivity(){
    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(binding.root)
        initViews()
        observeViewModels()
    }

    abstract fun getViewBinding(): VB

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            LoadingDialog.show(this@BaseActivity)
        } else {
            LoadingDialog.dismiss()
        }
    }
    protected abstract fun initViews()

    protected abstract fun observeViewModels()
}