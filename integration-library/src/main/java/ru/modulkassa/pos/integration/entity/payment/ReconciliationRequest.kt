package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.payment.RequestType.RECONCILIATION

/**
 * Запрос на сверку итогов
 */
class ReconciliationRequest : Bundable, PaymentRequest {

    override val requestType: RequestType
        get() = RECONCILIATION

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(RequestTypeSerialization.KEY, requestType.name)
        }
    }
}