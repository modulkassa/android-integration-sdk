package ru.modulkassa.pos.integration.core

import android.os.Bundle
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.service.IPluginServiceCallback

/**
 * Обработчик заданий от кассы
 */
abstract class OperationHandler(
    val name: String
) {
    abstract fun handle(data: Bundle, callback: PluginServiceCallbackHolder)
}