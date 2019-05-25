package com.jqk.mydemo.mvvmnew.base

import androidx.appcompat.app.AppCompatActivity
import com.jqk.mydemo.mvp.ProgressDialog

open class BaseView : AppCompatActivity() {
    private var progressDialog: ProgressDialog
    private var fragmentTransaction: androidx.fragment.app.FragmentTransaction

    init {
        progressDialog = ProgressDialog()
        fragmentTransaction = supportFragmentManager.beginTransaction()
    }

    fun showProgress() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog()
            fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(progressDialog, "ProgressDialog")
            if (!progressDialog.isAdded && !progressDialog.isVisible && !progressDialog.isRemoving) {
                fragmentTransaction.commitAllowingStateLoss()
            }
        } else {
            if (!progressDialog.isAdded && !progressDialog.isVisible && !progressDialog.isRemoving) {
                fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.add(progressDialog, "ProgressDialog")
                fragmentTransaction.commitAllowingStateLoss()
            }
        }
    }

    fun hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismissAllowingStateLoss()
        }
    }
}