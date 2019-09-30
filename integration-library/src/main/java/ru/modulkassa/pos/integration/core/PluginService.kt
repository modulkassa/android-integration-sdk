package ru.modulkassa.pos.integration.core

import android.app.Service
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.os.IBinder
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.lib.R
import ru.modulkassa.pos.integration.service.IPluginService
import ru.modulkassa.pos.integration.service.IPluginServiceCallback
import timber.log.Timber

abstract class PluginService : Service() {

    private var rescueAnswerSenderEngine: IntentSender? = null

    companion object {
        const val RESCUE_ANSWER_SENDER_KEY: String = "rescue_answer_sender"
    }

    private val binder = object : IPluginService.Stub() {
        override fun executeOperation(operationName: String?, data: Bundle?,
                                      callback: IPluginServiceCallback?) {
            if (operationName == null || data == null || callback == null) {
                Timber.w("operationName, data и callback не должны быть null. Пропускаем")
                return
            }
            val handler = handlers[operationName]
            if (handler != null) {
                handler.handle(data, PluginServiceCallbackHolder(callback, rescueAnswerSenderEngine))
            } else {
                Timber.w("Не найден обработчик для $operationName")
                callback.failed(getString(R.string.method_is_unsupported), Bundle.EMPTY)
            }
        }

    }

    private val handlers = HashMap<String, OperationHandler>()

    override fun onBind(intent: Intent?): IBinder? {
        rescueAnswerSenderEngine = intent?.extras?.getParcelable(RESCUE_ANSWER_SENDER_KEY)
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        createHandlers().forEach { operationHandler ->
            handlers[operationHandler.name] = operationHandler
        }
    }

    /**
     * Создает обработчики для запросов из Модулькассы
     *
     * Если какой-либо из запросов не поддерживается, для него можно опустить обработчик.
     * Модулькасса перед обращением к сервису проверяет возможность выполнения запроса.
     */
    abstract fun createHandlers(): List<OperationHandler>
}

