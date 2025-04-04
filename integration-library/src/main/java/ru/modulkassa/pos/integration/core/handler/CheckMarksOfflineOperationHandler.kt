package ru.modulkassa.pos.integration.core.handler

import android.os.Bundle
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.core.OperationHandler
import ru.modulkassa.pos.integration.entity.mark.CheckMarksOfflineRequest
import ru.modulkassa.pos.integration.entity.mark.MarkRequestType
import java.util.Locale

abstract class CheckMarksOfflineOperationHandler :
    OperationHandler(MarkRequestType.CHECK.name.toLowerCase(Locale.ROOT)) {

    override fun handle(data: Bundle, callback: PluginServiceCallbackHolder) {
        handleCheckMarksOffline(CheckMarksOfflineRequest.fromBundle(data), callback)
    }

    abstract fun handleCheckMarksOffline(request: CheckMarksOfflineRequest, callback: PluginServiceCallbackHolder)
}