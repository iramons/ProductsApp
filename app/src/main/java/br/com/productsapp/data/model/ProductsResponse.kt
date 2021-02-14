package br.com.productsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.productsapp.data.db.Databases
import com.squareup.moshi.Json

@Entity(tableName = Databases.ProductsResponse.TABLE_NAME)
data class ProductsResponse(
        @PrimaryKey(autoGenerate = true) val id:Int,
        @field:Json(name = "spotlight") val spotlight: List<Spotlight>? = null,
        @field:Json(name = "products") val products: List<Products>? = null,
        @field:Json(name = "cash") val cash: Cash? = null,
)