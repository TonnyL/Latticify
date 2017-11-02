package io.github.tonnyl.latticify.database.converters

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by lizhaotailang on 15/10/2017.
 */
object AccessTokenPermissionTypeConverters {

    @TypeConverter
    @JvmStatic
    fun stringListToString(stringList: List<String>): String = Gson().toJson(stringList)

    @TypeConverter
    @JvmStatic
    fun stringToStringList(string: String): List<String> {
        val typeToken = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(string, typeToken)
    }

}