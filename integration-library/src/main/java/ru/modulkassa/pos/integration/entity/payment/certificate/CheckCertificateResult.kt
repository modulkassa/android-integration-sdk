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
     * Хешированное значение pan карты
     */
    val panSha256: String
) : Bundable {

    companion object {
        private const val KEY_PAN = "pan"

        fun fromBundle(bundle: Bundle): CheckCertificateResult {
            return CheckCertificateResult(
                panSha256 = bundle.getString(KEY_PAN) ?: ""
            )
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(KEY_PAN, panSha256)
            putString(RequestTypeSerialization.KEY, RequestType.CHECK_CERTIFICATE.name)
        }
    }
}