package com.jqk.mydemo.mvvmnew.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.jqk.mydemo.mvp.ProgressDialog

open class BaseView : AppCompatActivity() {
    private var progressDialog: ProgressDialog
    private var fragmentTransaction: FragmentTransaction? = null

    init {
        progressDialog = ProgressDialog()
    }

    fun showProgress() {
        progressDialog.let {
            fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction?.add(it, "ProgressDialog")
            if (!it.isAdded && !it.isVisible && !it.isRemoving) {
                fragmentTransaction?.commitAllowingStateLoss()
            }
        }
    }

    fun hideProgress() {
        progressDialog.let {
            if (it.isAdded || it.isVisible || it.isRemoving) {
                it.dismissAllowingStateLoss()
            }
        }
    }
}