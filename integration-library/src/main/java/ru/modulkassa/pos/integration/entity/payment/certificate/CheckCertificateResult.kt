package ru.modulkassa.pos.integration.entity.payment.certificate

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.payment.RequestType
import ru.modulkassa.pos.integration.entity.payment.RequestTypeSerialization

/**
 * Результат проверки электронного сертификата
 */
class CheckCertificateResult(
    /**
     * Payment Account Reference карты
     */
    val par: String
) : Bundable {

    companion object {
        private const val KEY_PAR = "par"

        fun fromBundle(bundle: Bundle): CheckCertificateResult {
            return CheckCertificateResult(
                par = bundle.getString(KEY_PAR) ?: ""
            )
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(KEY_PAR, par)
            putString(RequestTypeSerialization.KEY, RequestType.CHECK_CERTIFICATE.name)
        }
    }
}