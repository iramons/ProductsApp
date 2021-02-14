package br.com.productsapp.util

import androidx.room.TypeConverter
import br.com.productsapp.data.model.Cash
import br.com.productsapp.data.model.Products
import br.com.productsapp.data.model.Spotlight
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {

    private var gson = Gson()

    // StringList
    @TypeConverter
    fun stringToSomeList(data: String?): List<String>? {
        if (data == null) return Collections.emptyList()
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson<List<String>>(data, listType)
    }

    @TypeConverter
    fun someListToString(someObjects: List<String>?): String {
        return gson.toJson(someObjects)
    }

    // SpotlightList
    @TypeConverter
    fun fromSpotlightList(list: List<Spotlight>?): String? {
        if (list == null) return null
        val type = object : TypeToken<List<Spotlight>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun toSpotlightList(listString: String?): List<Spotlight>? {
        if (listString == null) return null
        val type = object : TypeToken<List<Spotlight>>() {}.type
        return gson.fromJson<List<Spotlight>>(listString, type)
    }

    // ProductsList
    @TypeConverter
    fun fromProductsList(list: List<Products>?): String? {
        if (list == null) return null
        val type = object : TypeToken<List<Products>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun toProductsList(listString: String?): List<Products>? {
        if (listString == null) return null
        val type = object : TypeToken<List<Products>>() {}.type
        return gson.fromJson<List<Products>>(listString, type)
    }

    // CashList
    @TypeConverter
    fun fromCashList(list: List<Cash>?): String? {
        if (list == null) return null
        val type = object : TypeToken<List<Spotlight>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun toCashList(listString: String?): List<Cash>? {
        if (listString == null) return null
        val type = object : TypeToken<List<Cash>>() {}.type
        return gson.fromJson<List<Cash>>(listString, type)
    }

    // Cash
    @TypeConverter
    fun fromCash(obj: Cash?): String? {
        if (obj == null) return null
        val type = object : TypeToken<Spotlight>() {}.type
        return gson.toJson(obj, type)
    }

    @TypeConverter
    fun toCash(string: String?): Cash? {
        if (string == null) return null
        val type = object : TypeToken<Cash>() {}.type
        return gson.fromJson<Cash>(string, type)
    }

}

