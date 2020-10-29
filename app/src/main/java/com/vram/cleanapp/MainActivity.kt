package com.vram.cleanapp

import android.os.Bundle
import com.vram.cleanapp.shared.android.BaseActivity
import com.vram.cleanapp.shared.android.BaseViewModel
import com.vram.cleanapp.view.MainViewModel
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
//        viewModel
    }

    private fun initViews() {
        bLogin.setOnClickListener {

        }
    }
}