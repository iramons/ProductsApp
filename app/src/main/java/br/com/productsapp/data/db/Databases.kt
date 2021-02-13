package br.com.productsapp.data.db

interface Databases {

    object Spotlight {
        const val TABLE_NAME: String = "spotlight_table"

        const val name: String = "name"
        const val bannerURL: String = "bannerURL"
        const val description: String = "description"
    }

    object Products {
        const val TABLE_NAME: String = "products_table"

        const val name: String = "name"
        const val imageURL: String = "imageURL"
        const val description: String = "description"
    }

    object Cash {
        const val TABLE_NAME: String = "cash_table"

        const val title: String = "title"
        const val bannerURL: String = "bannerURL"
        const val description: String = "description"
    }


}