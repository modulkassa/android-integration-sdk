package ru.modulkassa.pos.integrationtest

import android.content.Intent
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.core.OperationHandler
import ru.modulkassa.pos.integration.core.PluginService
import ru.modulkassa.pos.integration.core.handler.LoyaltyOperationHandler
import ru.modulkassa.pos.integration.entity.loyalty.LoyaltyRequest

class SampleLoyaltyService : PluginService() {

    override fun createHandlers(): List<OperationHandler> {
        return listOf(
            object : LoyaltyOperationHandler(this@SampleLoyaltyService) {
                override fun handleLoyaltyRequest(loyaltyRequest: LoyaltyRequest,
                                                  callback: PluginServiceCallbackHolder) {
                    // запрос на расчет скидок обрабатывается в отдельной активити
                    val intent = Intent(applicationContext, SampleLoyaltyActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        putExtra(SampleLoyaltyActivity.LOYALTY_DATA, loyaltyRequest.toBundle())
                        callback.putToIntent(this)
                    }
                    startActivity(intent)
                }
            }
        )
    }
}