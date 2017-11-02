package io.github.tonnyl.latticify.database.converters

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.tonnyl.latticify.data.AccessTokenPermission

/**
 * Created by lizhaotailang on 15/10/2017.
 */
object AccessTokenTypeConverters {

    @TypeConverter
    @JvmStatic
    fun permissionListToString(permissions: List<AccessTokenPermission>): String {
        return Gson().toJson(permissions)
    }

    @TypeConverter
    @JvmStatic
    fun stringToPermissionList(string: String): List<AccessTokenPermission> {
        val typeToken = object : TypeToken<List<AccessTokenPermission>>() {}.type
        return Gson().fromJson(string, typeToken)
    }

}