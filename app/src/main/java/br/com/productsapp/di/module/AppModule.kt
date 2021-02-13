package br.com.productsapp.di.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import br.com.productsapp.BaseApp
import br.com.productsapp.data.db.AppDatabase
import br.com.productsapp.data.source.remote.service.ServiceGenerator
import br.com.productsapp.di.CustomViewModelFactory
import br.com.productsapp.di.annotation.AppBaseApplication
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @AppBaseApplication
    fun provideApplication(app : BaseApp): BaseApp = app

    @Provides
    @Singleton
    fun provideAppContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return AppDatabase.invoke(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideCustomViewModelFactory(customCustomViewModelFactory: CustomViewModelFactory): ViewModelProvider.Factory {
        return customCustomViewModelFactory
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideServiceGenerator(): ServiceGenerator {
        return ServiceGenerator()
    }
}