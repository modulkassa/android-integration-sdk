package ru.modulkassa.pos.integration.core.handler

import android.os.Bundle
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.core.OperationHandler
import ru.modulkassa.pos.integration.entity.crm.CreateOrderToCrmRequest
import ru.modulkassa.pos.integration.entity.crm.CrmRequestType

abstract class CreateOrderToCrmOperationHandler : OperationHandler(CrmRequestType.CREATE.name.toLowerCase()) {

    override fun handle(data: Bundle, callback: PluginServiceCallbackHolder) {
        handleCreateOrderToCrm(CreateOrderToCrmRequest.fromBundle(data), callback)
    }

    abstract fun handleCreateOrderToCrm(request: CreateOrderToCrmRequest, callback: PluginServiceCallbackHolder)
}