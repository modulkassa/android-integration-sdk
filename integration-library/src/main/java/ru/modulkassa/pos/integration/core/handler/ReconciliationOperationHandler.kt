package ru.modulkassa.pos.integration.core.handler

import android.os.Bundle
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.core.OperationHandler

/**
 * Обработчик запроса сверки итогов
 */
abstract class ReconciliationOperationHandler : OperationHandler(NAME) {

    companion object {
        const val NAME = "reconciliation"
    }

    override fun handle(data: Bundle, callback: PluginServiceCallbackHolder) {
        handleReconciliation(callback)
    }

    abstract fun handleReconciliation(callback: PluginServiceCallbackHolder)
}