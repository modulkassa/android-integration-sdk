package ru.modulkassa.pos.integration.core

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.RescueAnswerReceiver
import ru.modulkassa.pos.integration.lib.R
import ru.modulkassa.pos.integration.service.IPluginService
import ru.modulkassa.pos.integration.service.IPluginServiceCallback
import timber.log.Timber
import java.lang.ref.WeakReference

abstract class PluginService : Service() {

    private var startServiceIntent: Intent? = null
    private lateinit var rescueAnswerReceiver: RescueAnswerReceiver

    private val binder = object : IPluginService.Stub() {
        override fun executeOperation(operationName: String?, data: Bundle?,
                                      callback: IPluginServiceCallback?) {
            if (operationName == null || data == null || callback == null) {
                Timber.w("operationName, data и callback не должны быть null. Пропускаем")
                return
            }
            val handler = handlers[operationName]
            if (handler != null) {
                PluginServiceCallbackHolder.rescueAnswerReceiver = WeakReference(this@PluginService.rescueAnswerReceiver)
                handler.handle(data, PluginServiceCallbackHolder(callback))
            } else {
                Timber.w("Не найден обработчик для $operationName")
                callback.failed(getString(R.string.method_is_unsupported), Bundle.EMPTY)
            }
        }

    }

    private val handlers = HashMap<String, OperationHandler>()

    override fun onBind(intent: Intent?): IBinder? {
        startServiceIntent = intent
        rescueAnswerReceiver = PluginServiceRescueAnswerReceiver(this.applicationContext)
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

class PluginServiceRescueAnswerReceiver(
    private val context: Context
) : RescueAnswerReceiver {

    override fun succeeded(data: Bundle?) {
        val intent = Intent("ru.modulkassa.pos.RESCUE_SEND_ANSWER").apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("data", data)
            putExtra("mode", "loyalty")
        }
        context.startActivity(intent)
    }

    override fun failed(message: String?, extraData: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelled() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}