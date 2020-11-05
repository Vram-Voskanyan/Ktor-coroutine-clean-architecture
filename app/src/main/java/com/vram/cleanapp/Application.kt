package com.vram.cleanapp

import android.app.Application
import com.vram.cleanapp.data.service.network.KtorClient
import com.vram.cleanapp.data.service.network.NetworkApi
import com.vram.cleanapp.data.service.network.NetworkApiImpl
import com.vram.cleanapp.data.repo.LoginRepoImpl
import com.vram.cleanapp.data.repo.UserRepoImpl
import com.vram.cleanapp.data.repo.ValidationRepoImpl
import com.vram.cleanapp.domain.usecase.LoginUseCase
import com.vram.cleanapp.domain.usecase.LoginUseCaseImpl
import com.vram.cleanapp.domain.repo.LoginRepo
import com.vram.cleanapp.domain.repo.UserRepo
import com.vram.cleanapp.domain.repo.ValidationRepo
import com.vram.cleanapp.domain.usecase.UserUseCase
import com.vram.cleanapp.domain.usecase.UserUseCaseImpl
import com.vram.cleanapp.view.MainViewModel
import kotlinx.serialization.InternalSerializationApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@InternalSerializationApi
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
        single<NetworkApi> { NetworkApiImpl(KtorClient(getString(R.string.base_url))) } // TODO: Base URl

        // Repo
        single<LoginRepo> { LoginRepoImpl(get()) }
        single<UserRepo> { UserRepoImpl(get()) }
        single<ValidationRepo> { ValidationRepoImpl() }

        // UseCase
        single<LoginUseCase> { LoginUseCaseImpl(get(), get(), get()) }
        single<UserUseCase> { UserUseCaseImpl(get()) }

        // ViewModel
        viewModel { MainViewModel(get(), get()) }
    }

}