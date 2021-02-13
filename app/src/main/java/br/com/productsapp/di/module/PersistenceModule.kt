package br.com.productsapp.di.module

import androidx.annotation.NonNull
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

//    @Binds
//    @IntoMap
//    @Singleton
//    @AppRepository(AllProductsRepository::class)
//    abstract fun bindAllProductsRepository(repository: AllProductsRepository): AllProductsRepository

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