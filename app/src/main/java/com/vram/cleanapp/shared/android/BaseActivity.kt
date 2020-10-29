package com.vram.cleanapp.shared.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vram.cleanapp.shared.data.EventObserver

abstract class BaseActivity : AppCompatActivity() {

    abstract val baseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        baseViewModel.showError.observe(this, EventObserver {
            showErrorPopup(it)
        })
        baseViewModel.showSuccess.observe(this, EventObserver {
            showSuccessSnackbar(it)
        })
        // TODO -> show loading, hide loading
    }

}
