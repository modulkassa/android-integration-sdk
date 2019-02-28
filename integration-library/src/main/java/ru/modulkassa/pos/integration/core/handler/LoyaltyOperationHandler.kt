package ru.modulkassa.pos.integration.core.handler

import android.content.Context
import android.os.Bundle
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.core.OperationHandler
import ru.modulkassa.pos.integration.entity.loyalty.LoyaltyInvalidStructureException
import ru.modulkassa.pos.integration.entity.loyalty.LoyaltyRequest
import ru.modulkassa.pos.integration.lib.R
import timber.log.Timber

/**
 * Обработчик запросов к системе лояльности
 */
abstract class LoyaltyOperationHandler(
    private val context: Context
) : OperationHandler(NAME) {

    companion object {
        const val NAME = "loyalty-request"
    }

    override fun handle(data: Bundle, callback: PluginServiceCallbackHolder) {
        try {
            handleLoyaltyRequest(LoyaltyRequest.fromBundle(data), callback)
        } catch (e: LoyaltyInvalidStructureException) {
            Timber.w(e)
            callback.get().failed(context.getString(R.string.loyalty_request_is_invalid), Bundle.EMPTY)
        }
    }

    abstract fun handleLoyaltyRequest(loyaltyRequest: LoyaltyRequest, callback: PluginServiceCallbackHolder)

}