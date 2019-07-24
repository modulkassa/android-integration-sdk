package ru.modulkassa.pos.integration.core.handler

import android.os.Bundle
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.core.OperationHandler
import ru.modulkassa.pos.integration.entity.payment.PayRequest
import ru.modulkassa.pos.integration.entity.payment.RequestType

abstract class PayOperationHandler : OperationHandler(RequestType.PAY.name.toLowerCase()) {

    override fun handle(data: Bundle, callback: PluginServiceCallbackHolder) {
        handlePay(PayRequest.fromBundle(data), callback)
    }

    abstract fun handlePay(payRequest: PayRequest, callback: PluginServiceCallbackHolder)
}