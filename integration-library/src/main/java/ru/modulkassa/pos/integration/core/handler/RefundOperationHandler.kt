package ru.modulkassa.pos.integration.core.handler

import android.os.Bundle
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.core.OperationHandler
import ru.modulkassa.pos.integration.entity.payment.RefundRequest
import ru.modulkassa.pos.integration.entity.payment.RequestType

abstract class RefundOperationHandler : OperationHandler(RequestType.REFUND.name.toLowerCase()) {

    override fun handle(data: Bundle, callback: PluginServiceCallbackHolder) {
        handleRefund(RefundRequest.fromBundle(data), callback)
    }

    abstract fun handleRefund(refundRequest: RefundRequest, callback: PluginServiceCallbackHolder)
}