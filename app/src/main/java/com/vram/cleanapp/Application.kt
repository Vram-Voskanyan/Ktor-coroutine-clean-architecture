package com.vram.cleanapp
// todo add license to all files
import android.app.Application
import com.vram.cleanapp.data.network.NetworkApi
import com.vram.cleanapp.service.network.KtorClient
import com.vram.cleanapp.data.network.NetworkApiImpl
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
import com.vram.cleanapp.presenter.MainViewModel
import com.vram.cleanapp.service.cache.SharedPrefs
import com.vram.cleanapp.service.cache.SharedPrefsImpl
import com.vram.cleanapp.service.serialization.KXSerializationImpl
import com.vram.cleanapp.service.serialization.SerializationWrapper
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
        // Service
        single<SerializationWrapper> { KXSerializationImpl() }
        single<NetworkApi> { initRestClient(get()) }
        single<SharedPrefs> { SharedPrefsImpl(applicationContext) }

        // Repo
        single<LoginRepo> { LoginRepoImpl(get()) }
        single<UserRepo> { UserRepoImpl(get(), get()) }
        single<ValidationRepo> { ValidationRepoImpl() }

        // UseCase
        single<LoginUseCase> { LoginUseCaseImpl(get(), get(), get()) }
        single<UserUseCase> { UserUseCaseImpl(get()) }

        // ViewModel
        viewModel { MainViewModel(get(), get()) }
    }

    // Can be moved into some factory class
    private fun initRestClient(serializationWrapper: SerializationWrapper): NetworkApiImpl {
        // init client & base url: http://example.com/api
        // have attention we have `path` on base url "/api"
        val ktorClient = KtorClient(BASE_URL, serializationWrapper)
        // init default headers
        // we use Json for request & response
        ktorClient.addDefaultHeader("Content-Type", "application/json")
        return NetworkApiImpl(ktorClient)
    }

}