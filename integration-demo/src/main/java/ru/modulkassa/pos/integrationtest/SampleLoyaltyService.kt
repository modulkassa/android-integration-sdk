package ru.modulkassa.pos.integrationtest

import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.core.OperationHandler
import ru.modulkassa.pos.integration.core.PluginService
import ru.modulkassa.pos.integration.core.handler.LoyaltyOperationHandler
import ru.modulkassa.pos.integration.entity.loyalty.LoyaltyPositionImpact
import ru.modulkassa.pos.integration.entity.loyalty.LoyaltyRequest
import ru.modulkassa.pos.integration.entity.loyalty.LoyaltyResult
import java.math.BigDecimal
import java.util.UUID

class SampleLoyaltyService : PluginService() {

    override fun createHandlers(): List<OperationHandler> {
        return listOf(
            object : LoyaltyOperationHandler(this@SampleLoyaltyService) {
                override fun handleLoyaltyRequest(
                    loyaltyRequest: LoyaltyRequest,
                    callback: PluginServiceCallbackHolder
                ) {
                    val impacts = loyaltyRequest.positions.mapIndexed { index, position ->
                        LoyaltyPositionImpact(
                            id = UUID.randomUUID().toString(),
                            positionId = position.id,
                            price = BigDecimal.TEN.multiply(BigDecimal.valueOf(index.toLong() + 1)),
                            quantity = position.quantity
                        )
                    }
                    callback.get().succeeded(
                        LoyaltyResult(data = "SampleLoyalty", impacts = impacts).toBundle()
                    )
                }
            }
        )
    }
}