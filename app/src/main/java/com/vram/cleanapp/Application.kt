package com.vram.cleanapp

import android.app.Application
import com.vram.cleanapp.shared.network.KtorClient
import com.vram.cleanapp.data.service.NetworkApi
import com.vram.cleanapp.data.service.NetworkApiImpl
import com.vram.cleanapp.data.repo.LoginRepo
import com.vram.cleanapp.data.repo.LoginRepoImpl
import com.vram.cleanapp.domain.LoginUseCase
import com.vram.cleanapp.domain.LoginUseCaseImpl
import com.vram.cleanapp.view.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(appModule)
        }
    }

    private val appModule = module {
        single<NetworkApi> { NetworkApiImpl(KtorClient(getString(R.string.app_name))) } // TODO: Base URl

        single<LoginUseCase> { LoginUseCaseImpl() }
        single<LoginRepo> { LoginRepoImpl(get()) }

        viewModel { MainViewModel(get()) }
    }

}