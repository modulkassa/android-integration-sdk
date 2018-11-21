package ru.modulkassa.pos.integration.intent

import android.content.Intent

/**
 * Intent для коннекта к сервису приложения МодульКасса, которое настроено на production окружение
 */
class ModulKassaServiceIntent : Intent() {
    init {
        `package` = "com.avanpos.pos"
        action = "ru.modulkassa.pos.MODULKASSA_SERVICE_INTENT"
    }
}
/**
 * Intent для коннекта к сервису приложения МодульКасса, которое настроено на staging окружение
 */
class StagingModulKassaServiceIntent : Intent() {
    init {
        `package` = "com.avanpos.pos.staging"
        action = "ru.modulkassa.pos.MODULKASSA_SERVICE_INTENT"
    }
}
/**
 * Intent для коннекта к сервису приложения МодульКасса, которое настроено на RC окружение
 */
class RcModulKassaServiceIntent : Intent() {
    init {
        `package` = "com.avanpos.pos.rc"
        action = "ru.modulkassa.pos.MODULKASSA_SERVICE_INTENT"
    }
}