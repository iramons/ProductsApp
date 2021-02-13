package br.com.productsapp.data.dao

import androidx.annotation.Size
import androidx.room.*
import br.com.productsapp.data.db.Databases
import br.com.productsapp.data.model.Cash
import kotlinx.coroutines.flow.Flow

@Dao
interface CashDAO {

    val db
        get() = Databases.Cash

    @Query("SELECT * FROM ${db.TABLE_NAME}")
    fun read(): Flow<Cash>

    @Query("SELECT * FROM ${db.TABLE_NAME}")
    fun get(): Cash

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(@Size(min = 1) vararg model: Cash)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(@Size(min = 1) list: List<Cash>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(@Size(min = 1) model: Cash)

    @Delete
    suspend fun delete(@Size(min = 1) model: Cash)
}