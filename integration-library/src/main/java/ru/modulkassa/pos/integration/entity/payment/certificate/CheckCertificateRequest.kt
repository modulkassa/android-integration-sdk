package ru.modulkassa.pos.integration.entity.payment.certificate

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.payment.PaymentRequest
import ru.modulkassa.pos.integration.entity.payment.RequestType
import ru.modulkassa.pos.integration.entity.payment.RequestType.CHECK_CERTIFICATE
import ru.modulkassa.pos.integration.entity.payment.RequestTypeSerialization

/**
 * Запрос проверки электронного сертификата
 */
class CheckCertificateRequest : Bundable, PaymentRequest {

    override val requestType: RequestType
        get() = CHECK_CERTIFICATE

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(RequestTypeSerialization.KEY, requestType.name)
        }
    }
}