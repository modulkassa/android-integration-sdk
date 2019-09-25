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
                    val intent = Intent(applicationContext, DummyActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        putExtra(DummyActivity.LOYALTY_DATA, loyaltyRequest.toBundle())
                        callback.putToIntent(this)
                    }
                    startActivity(intent)

//                    val impacts = loyaltyRequest.positions.mapIndexed { index, position ->
//                        LoyaltyPositionImpact(
//                            id = UUID.randomUUID().toString(),
//                            positionId = position.id,
//                            price = BigDecimal.TEN.multiply(BigDecimal.valueOf(index.toLong() + 1)),
//                            quantity = position.quantity
//                        )
//                    }
//                    callback.get().succeeded(
//                        LoyaltyResult(data = "SampleLoyalty", impacts = impacts).toBundle()
//                    )
                }
            }
        )
    }
}