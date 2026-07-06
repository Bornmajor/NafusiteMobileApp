package com.majasociet.nafusitemobileapp.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.majasociet.nafusitemobileapp.features.auth.data.models.BasicRegistrationInfo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavTypes {
    val BasicRegistrationInfoType = object : NavType<BasicRegistrationInfo>(isNullableAllowed = false) {

        override fun put(bundle: Bundle, key: String, value: BasicRegistrationInfo) {
            bundle.putString(key, Json.encodeToString(BasicRegistrationInfo.serializer(), value))
        }

        override fun get(bundle: Bundle, key: String): BasicRegistrationInfo? {
            return bundle.getString(key)?.let { Json.decodeFromString(BasicRegistrationInfo.serializer(), it) }
        }

        override fun parseValue(value: String): BasicRegistrationInfo {
            return Json.decodeFromString(BasicRegistrationInfo.serializer(), Uri.decode(value))
        }

        override fun serializeAsValue(value: BasicRegistrationInfo): String {
            return Uri.encode(Json.encodeToString(BasicRegistrationInfo.serializer(), value))
        }
    }

    val StringListType = object : NavType<List<String>>(isNullableAllowed = false) {
        override fun put(bundle: Bundle, key: String, value: List<String>) {
            bundle.putStringArrayList(key, ArrayList(value))
        }

        override fun get(bundle: Bundle, key: String): List<String>? {
            return bundle.getStringArrayList(key)
        }

        override fun parseValue(value: String): List<String> {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: List<String>): String {
            return Uri.encode(Json.encodeToString(value))
        }
    }
}