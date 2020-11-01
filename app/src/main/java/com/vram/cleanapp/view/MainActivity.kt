package com.vram.cleanapp.view

import android.os.Bundle
import com.vram.cleanapp.R
import com.vram.cleanapp.view.core.BaseActivity
import com.vram.cleanapp.view.core.BaseViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModel()
    override val baseViewModel: BaseViewModel by lazy { viewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initObservers()
    }

    private fun initObservers() {

    }

    private fun initViews() {
        bLogin.setOnClickListener {
            viewModel.login(etEmail.text.toString(), etPassword.text.toString())
        }
    }
}