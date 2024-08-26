package com.packt.android

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.Random

class MainApplication : Application() {
    val appModule = module {
        single {
            Random()
        }
        single<NumberRepository> {
            NumberRepositoryImpl(get())
        }
    }
    val mainModule = module {
        viewModel {
            MainViewModel(get())
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(appModule, mainModule))
        }

    }

}
