package ru.modulkassa.pos.integration

import android.os.Bundle
import android.os.DeadObjectException
import ru.modulkassa.pos.integration.service.IPluginServiceCallback
import timber.log.Timber

class RescueCallbackWrapper(
    private val origin: IPluginServiceCallback,
    private val rescueAnswerReceiver: RescueAnswerReceiver?
) : IPluginServiceCallback by origin {

    override fun succeeded(data: Bundle?) {
        try {
            origin.succeeded(data)
        } catch (e: DeadObjectException) {
            Timber.w(e.message)
            rescueAnswerReceiver?.succeeded(data)
        }
    }

    override fun failed(message: String?, extraData: Bundle?) {
        try {
            origin.failed(message, extraData)
        } catch (e: DeadObjectException) {
            Timber.w(e.message)
            rescueAnswerReceiver?.failed(message, extraData)
        }
    }

    override fun cancelled() {
        try {
            origin.cancelled()
        } catch (e: DeadObjectException) {
            Timber.w(e.message)
            rescueAnswerReceiver?.cancelled()
        }
    }

}