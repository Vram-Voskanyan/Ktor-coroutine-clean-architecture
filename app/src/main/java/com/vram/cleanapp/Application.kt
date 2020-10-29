package com.vram.cleanapp

import android.app.Application
import com.vram.cleanapp.network.KtorClient
import com.vram.cleanapp.network.NetworkApi
import com.vram.cleanapp.network.NetworkApiImpl
import com.vram.cleanapp.repo.LoginRepo
import com.vram.cleanapp.repo.LoginRepoImpl
import com.vram.cleanapp.view.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(appModule)
        }
    }

    private val appModule = module {
        single<NetworkApi> { NetworkApiImpl(ktorClient = KtorClient(getString(R.string.app_name))) }
        single<LoginRepo> { LoginRepoImpl(get()) }
        viewModel { MainViewModel() }
    }

}