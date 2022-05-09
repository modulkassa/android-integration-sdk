package ru.modulkassa.pos.integration.core.manager

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable

/**
 * Информация о клиентском приложении
 */
data class ClientInfo(
    /**
     * Версия SDK
     */
    val sdkVersion: String,
    /**
     * Имя пакета приложения, которое использует SDK
     */
    val clientPackageName: String,
    /**
     * Версия приложения, которое использует SDK
     */
    val clientAppVersion: String
) : Bundable {

    companion object {
        private const val KEY_SDK_VERSION = "sdk_version"
        private const val KEY_CLIENT_PACKAGE_NAME = "client_package_name"
        private const val KEY_CLIENT_APP_VERSION = "client_app_version"

        fun fromBundle(data: Bundle): ClientInfo {
            return ClientInfo(
                data.getString(KEY_SDK_VERSION, "unknown"),
                data.getString(KEY_CLIENT_PACKAGE_NAME, "unknown"),
                data.getString(KEY_CLIENT_APP_VERSION, "unknown")
            )
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(KEY_SDK_VERSION, sdkVersion)
            putString(KEY_CLIENT_PACKAGE_NAME, clientPackageName)
            putString(KEY_CLIENT_APP_VERSION, clientAppVersion)
        }
    }

}