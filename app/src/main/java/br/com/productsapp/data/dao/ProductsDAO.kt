package br.com.productsapp.data.dao

import androidx.annotation.Size
import androidx.room.*
import br.com.productsapp.data.db.Databases
import br.com.productsapp.data.model.Products
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDAO {

    val db
    get() = Databases.Products

    @Query("SELECT * FROM ${db.TABLE_NAME}")
    fun read(): Flow<Products>

    @Query("SELECT * FROM ${db.TABLE_NAME}")
    fun get(): Products

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(@Size(min = 1) vararg model: Products)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(@Size(min = 1) list: List<Products>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(@Size(min = 1) model: Products)

    @Delete
    suspend fun delete(@Size(min = 1) model: Products)
}