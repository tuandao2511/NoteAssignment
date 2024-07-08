package com.example.core.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.core.ui.view.LoadingDialog

abstract class BaseFragment<VB : ViewBinding>(private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflateMethod(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModels()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            LoadingDialog.show(context)
        } else {
            LoadingDialog.dismiss()
        }
    }

    protected abstract fun initViews()

    protected abstract fun observeViewModels()
}