package br.com.productsapp.data.dao

import androidx.annotation.Size
import androidx.room.*
import br.com.productsapp.data.db.Databases
import br.com.productsapp.data.model.Spotlight
import kotlinx.coroutines.flow.Flow


@Dao
interface SpotlightDAO {

    val db
        get() = Databases.Spotlight

    @Query("SELECT * FROM ${db.TABLE_NAME}")
    fun read(): Flow<Spotlight>

    @Query("SELECT * FROM ${db.TABLE_NAME}")
    fun get(): Spotlight

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(@Size(min = 1) vararg model: Spotlight)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(@Size(min = 1) list: List<Spotlight>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(@Size(min = 1) model: Spotlight)

    @Delete
    suspend fun delete(@Size(min = 1) model: Spotlight)
}