package ru.modulkassa.pos.integration.core.manager

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import ru.modulkassa.pos.integration.lib.BuildConfig

class IntentFactory(
    private val context: Context
) {

    private val clientInfo = buildClientInfo(context)

    fun createIntent(): Intent {
        return Intent().apply {
            putExtras(clientInfo.toBundle())
        }
    }

    @Suppress("DEPRECATION")
    fun buildClientInfo(context: Context): ClientInfo {
        var clientVersion = "unknown"
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            clientVersion = "${packageInfo.versionName}(${packageInfo.versionCode})"
        } catch (error: PackageManager.NameNotFoundException) {
            // ignore
        }
        return ClientInfo(
            BuildConfig.VERSION_NAME,
            context.packageName,
            clientVersion
        )
    }
}