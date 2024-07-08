package com.example.core.ui.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.example.core.ui.R
import timber.log.Timber

object LoadingDialog {
    private var progressDialogBuilder: AlertDialog.Builder? = null
    private var progressDialog: AlertDialog? = null

    fun show(
        context: Context?,
        message: String? = null,
        isShowBackground: Boolean = false
    ) {
        if (context is Activity) {
            if (context.isFinishing) {
                return
            }
        }
        context?.let {
            try {
                progressDialogBuilder = AlertDialog.Builder(it, R.style.CoreLoadingTheme)

                if (progressDialog != null) {
                    if (progressDialog?.isShowing == false) {
                        progressDialog?.show()
                    }
                } else {
                    progressDialog = progressDialogBuilder?.create()
                    val useBackground = !message.isNullOrEmpty() || isShowBackground
                    configAlertWindow(progressDialog?.window, useBackground)
                    progressDialog?.show()
                }
            } catch (e: Exception) {
                Timber.e(e.stackTraceToString())
                return
            }
            progressDialog?.let { dialog ->
                dialog.window?.apply {
                    setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.core_progress_dialog)
                setMessageView(dialog, message)
            }
        }
    }

    private fun configAlertWindow(window: Window?, isShowBackground: Boolean) {
        window?.apply {
            val backgroundColor =
                if (isShowBackground) Color.WHITE else Color.TRANSPARENT
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = backgroundColor
            requestFeature(Window.FEATURE_NO_TITLE)
            setBackgroundDrawable(ColorDrawable(backgroundColor))
        }
    }

    private fun setMessageView(dialog: AlertDialog, message: String?) {
        val tvMessage = dialog.findViewById<TextView>(R.id.tv_message)
        if (!message.isNullOrEmpty()) {
            tvMessage.text = message
            tvMessage.visibility = View.VISIBLE
        } else {
            tvMessage.visibility = View.GONE
        }
    }

    fun dismiss() {
        try {
            progressDialog?.let {
                if (progressDialog?.isShowing == true) {
                    progressDialog?.dismiss()
                    progressDialog = null
                    progressDialogBuilder = null
                }
            }
        } catch (e: Exception) {
            Timber.e(e.stackTraceToString())
        }
    }
}