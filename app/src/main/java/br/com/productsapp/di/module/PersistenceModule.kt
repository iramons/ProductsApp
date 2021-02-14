package br.com.productsapp.di.module

import androidx.annotation.NonNull
import br.com.productsapp.data.dao.AllProductsDAO
import br.com.productsapp.data.dao.CashDAO
import br.com.productsapp.data.dao.ProductsDAO
import br.com.productsapp.data.dao.SpotlightDAO
import br.com.productsapp.data.db.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun provideAllProductsDAO(@NonNull database: AppDatabase): AllProductsDAO {
        return database.allProductsDAO()
    }

    @Provides
    @Singleton
    fun provideProductsDAO(@NonNull database: AppDatabase): ProductsDAO {
        return database.productsDAO()
    }

    @Provides
    @Singleton
    fun provideSpotlightDAO(@NonNull database: AppDatabase): SpotlightDAO {
        return database.spotlightDAO()
    }

    @Provides
    @Singleton
    fun provideCashDAO(@NonNull database: AppDatabase): CashDAO {
        return database.cashDAO()
    }

}