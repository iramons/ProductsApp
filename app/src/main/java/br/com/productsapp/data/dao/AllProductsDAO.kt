package br.com.productsapp.data.dao

import androidx.annotation.Size
import androidx.room.*
import br.com.productsapp.data.db.Databases
import br.com.productsapp.data.model.Cash
import br.com.productsapp.data.model.ProductsResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface AllProductsDAO {

    val db
        get() = Databases.ProductsResponse


    @Query("SELECT * FROM ${db.TABLE_NAME}")
    fun read(): Flow<ProductsResponse>

    @Query("SELECT * FROM ${db.TABLE_NAME}")
    fun get(): ProductsResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(@Size(min = 1) vararg model: ProductsResponse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(@Size(min = 1) list: List<ProductsResponse>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(@Size(min = 1) model: ProductsResponse)

    @Delete
    fun delete(@Size(min = 1) model: ProductsResponse)


}