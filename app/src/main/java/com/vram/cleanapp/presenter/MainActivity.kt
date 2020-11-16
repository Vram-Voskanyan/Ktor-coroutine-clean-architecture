package com.vram.cleanapp.presenter

import android.os.Bundle
import android.view.View
import com.vram.cleanapp.R
import com.vram.cleanapp.presenter.core.BaseActivity
import com.vram.cleanapp.presenter.core.BaseViewModel
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
        viewModel.onEmailError.observe(this, {
            etEmail.error = "Change Email with: vram.arm@gmail.com"
        })
        viewModel.onPasswordError.observe(this, {
            etPassword.error = "Psss~~\nChange it with: 1111"
        })
        viewModel.userNotesLoadingShow.observe(this, {
            pbUserNotesLoading.visibility = View.VISIBLE
        })
        viewModel.userNotesLoadingHide.observe(this, {
            pbUserNotesLoading.visibility = View.INVISIBLE
        })
        viewModel.userNotes.observe(this, {
            tvUserNotes.text = it
        })
        viewModel.userInfoLoadingShow.observe(this, {
            pbUserInfoLoading.visibility = View.VISIBLE
        })
        viewModel.userInfoLoadingHide.observe(this, {
            pbUserInfoLoading.visibility = View.INVISIBLE
        })
        viewModel.userInfo.observe(this, {
            tvUserInfo.text = it
        })
    }

    private fun initViews() {
        bLogin.setOnClickListener {
            viewModel.login(etEmail.text.toString(), etPassword.text.toString())
        }
        bResetData.setOnClickListener {
            resetAll()
        }
    }

    private fun resetAll() {
        viewModel.resetAll()
        pbUserNotesLoading.visibility = View.INVISIBLE
        tvUserNotes.text = ""
        tvUserInfo.text = ""
    }
}