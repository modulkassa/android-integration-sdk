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

/**
 * Intent для коннекта к сервису приложения Модулькасса из маркета (RuStore),
 * устанавливается в основном на телефоны, также настроено на production окружение
 */
class ModulKassaMarketServiceIntent : Intent() {
    init {
        `package` = "ru.modulkassa.android.apps.pos"
        action = "ru.modulkassa.pos.MODULKASSA_SERVICE_INTENT"
    }
}