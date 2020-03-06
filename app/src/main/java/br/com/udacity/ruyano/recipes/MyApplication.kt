package br.com.udacity.ruyano.recipes

import android.app.Application
import br.com.udacity.ruyano.recipes.di.Injection
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(Injection.modules)
        }
    }

}