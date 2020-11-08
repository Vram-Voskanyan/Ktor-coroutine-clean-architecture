package com.vram.cleanapp.presenter.core

import android.app.Activity
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.vram.cleanapp.R

fun Activity.showErrorPopup(message: String, title: String = getString(R.string.error)) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .show();
}

fun Activity.showSuccessSnackbar(message: String) {
    Snackbar.make(
        window.decorView.findViewById<ViewGroup>(android.R.id.content),
        message,
        Snackbar.LENGTH_LONG
    ).show()
}