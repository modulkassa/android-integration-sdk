package ru.modulkassa.pos.integration.core

/**
 * Константы, которые используются при взаимодействии между приложением и Модулькассой
 */
class ModulKassaApi {
    companion object {
        // регистрация чеков
        const val ACTION_CHECK_REGISTRATION = "ru.modulkassa.pos.CHECK_REGISTRATION"
        const val KEY_CASH_DOCUMENT_TYPE = "cash_doc_type"
        // открытие закрытие смены
        const val ACTION_SHIFT = "ru.modulkassa.pos.ACTION_SHIFT"
        const val KEY_SHIFT_REQUEST_TYPE = "shift_request_type"
        const val OPEN_SHIFT_REQUEST = "OpenShift"
        const val CLOSE_SHIFT_REQUEST = "CloseShift"
        const val X_REPORT_SHIFT_REQUEST = "XReport"
        // регистрация чека внесения/выема
        const val ACTION_MONEY_CHECK_REGISTRATION = "ru.modulkassa.pos.MONEY_CHECK_REGISTRATION"
    }
}