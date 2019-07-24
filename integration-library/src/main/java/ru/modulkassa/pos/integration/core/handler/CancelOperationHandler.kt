package ru.modulkassa.pos.integration.core.handler

import android.os.Bundle
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.core.OperationHandler
import ru.modulkassa.pos.integration.entity.payment.CancelRequest
import ru.modulkassa.pos.integration.entity.payment.RequestType

abstract class CancelOperationHandler : OperationHandler(RequestType.CANCEL.name.toLowerCase()) {

    override fun handle(data: Bundle, callback: PluginServiceCallbackHolder) {
        handleCancel(CancelRequest.fromBundle(data), callback)
    }

    abstract fun handleCancel(cancelRequest: CancelRequest, callback: PluginServiceCallbackHolder)
}