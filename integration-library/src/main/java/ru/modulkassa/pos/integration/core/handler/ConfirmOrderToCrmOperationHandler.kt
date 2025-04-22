package ru.modulkassa.pos.integration.core.handler

import android.os.Bundle
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.core.OperationHandler
import ru.modulkassa.pos.integration.entity.crm.ConfirmOrderToCrmRequest
import ru.modulkassa.pos.integration.entity.crm.CrmRequestType

abstract class ConfirmOrderToCrmOperationHandler :
    OperationHandler(CrmRequestType.CONFIRM_FISCALED.name.toLowerCase()) {

    override fun handle(data: Bundle, callback: PluginServiceCallbackHolder) {
        handleConfirmOrderToCrm(ConfirmOrderToCrmRequest.fromBundle(data), callback)
    }

    abstract fun handleConfirmOrderToCrm(request: ConfirmOrderToCrmRequest, callback: PluginServiceCallbackHolder)
}