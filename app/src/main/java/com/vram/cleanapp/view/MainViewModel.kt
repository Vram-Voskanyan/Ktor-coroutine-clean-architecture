package com.vram.cleanapp.view

import com.vram.cleanapp.shared.android.BaseViewModel
import com.vram.cleanapp.shared.android.runOnBackground
import kotlinx.coroutines.delay

class MainViewModel() : BaseViewModel() {


    fun login(userName: String?, password: String?) = runOnBackground {
        startLoading()
        // TODO: loginUsecase.login(userName: String?, password: String?) -> Action<User>
        delay(5000)
        showSuccess("Login success")
        delay(5000)
        showError("hmmmm :(")
        dataReceived()
        // TODO: end of UI testing
    }

    // TODO: add repos
//class MainViewModel(val loginRepo: LoginRepo, val dataListRepo: DataListRepo) : BaseViewModel() {


}