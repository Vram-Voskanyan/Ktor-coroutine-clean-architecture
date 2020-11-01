package com.vram.cleanapp.view.core

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

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
        baseViewModel.viewState.observe(this, {
            changeLoadingState(it ?: throw IllegalArgumentException("ViewState can't be null"))
        })
    }

    private fun changeLoadingState(viewState: ViewState) {
        when (viewState) {
            ViewState.DATA_LOADED -> progressBarView.visibility = View.INVISIBLE
            ViewState.LOADING -> progressBarView.visibility = View.VISIBLE
        }
    }

}
