package ru.modulkassa.pos.integration.intent

import android.content.Intent

/**
 * Intent для коннекта к сервису приложения Модулькасса, которое настроено на production окружение
 */
class ModulKassaServiceIntent : Intent() {
    init {
        `package` = "com.avanpos.pos"
        action = "ru.modulkassa.pos.MODULKASSA_SERVICE_INTENT"
    }
}