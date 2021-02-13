package br.com.productsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.productsapp.commom.base.BaseItemModel
import br.com.productsapp.commom.base.IItemTypeFactory
import br.com.productsapp.data.db.Databases
import com.squareup.moshi.Json

@Entity(tableName = Databases.Cash.TABLE_NAME)
data class Cash(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @field:Json(name = "title") val title: String? = null,
    @field:Json(name = "bannerURL") val bannerURL: String? = null,
    @field:Json(name = "description") val description: String? = null,
) : BaseItemModel()
{
    override fun type(IItemTypeFactory: IItemTypeFactory): Int {
        return IItemTypeFactory.type(this)
    }
}