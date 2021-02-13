package br.com.productsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.productsapp.data.dao.CashDAO
import br.com.productsapp.data.dao.ProductsDAO
import br.com.productsapp.data.dao.SpotlightDAO
import br.com.productsapp.data.model.Cash
import br.com.productsapp.data.model.Products
import br.com.productsapp.data.model.Spotlight
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(
        entities = [
            Spotlight::class,
            Products::class,
            Cash::class],
        exportSchema = false,
        version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productsDAO(): ProductsDAO
    abstract fun spotlightDAO(): SpotlightDAO
    abstract fun cashDAO(): CashDAO

    object Props {
        const val DB_NAME: String = "productsapp-room.db"
    }

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        private const val NUMBER_OF_THREADS = 4
        private val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        @Synchronized
        operator fun invoke(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(AppDatabase::class.java) { buildDatabase(context) }
        }

        @Synchronized
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                Props.DB_NAME
            )
                .fallbackToDestructiveMigration()
                .setTransactionExecutor(databaseWriteExecutor)
                //.addCallback(DB_CALLBACK)
                .build()
                .also { INSTANCE = it }
        }

        private val DB_CALLBACK = object : RoomDatabase.Callback() {
        }
    }
}