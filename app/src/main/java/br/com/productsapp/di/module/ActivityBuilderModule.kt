package br.com.productsapp.di.module

import br.com.productsapp.di.annotation.ActivityScoped
import br.com.productsapp.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity


}
