package ru.modulkassa.pos.integration.core.handler

import android.os.Bundle
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.core.OperationHandler
import ru.modulkassa.pos.integration.entity.payment.RefundRequest

abstract class RefundOperationHandler : OperationHandler(NAME) {

    companion object {
        const val NAME = "refund"
    }

    override fun handle(data: Bundle, callback: PluginServiceCallbackHolder) {
        handleRefund(RefundRequest.fromBundle(data), callback)
    }

    abstract fun handleRefund(refundRequest: RefundRequest, callback: PluginServiceCallbackHolder)
}