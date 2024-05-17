package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import com.google.gson.reflect.TypeToken
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.GsonFactory
import ru.modulkassa.pos.integration.entity.payment.RequestType.CANCEL
import java.math.BigDecimal

/**
 * Запрос для отмены операции
 */
data class CancelRequest(
    /**
     * Идентификатор оплаты, который нужно отменить
     */
    val paymentId: String,
    /**
     * Сумма оплаты
     */
    val amount: BigDecimal,
    /**
     * Описание отмены
     */
    val description: String,
    /**
     * Дополнительная информация, которая была передана в результате оплаты
     * **Внимание!** МодульКасса не ограничивает кассира в выборе способа оплаты при возврате.
     * Поэтому, поле может быть не заполнено или заполнено другими данными.
     */
    val paymentInfo: String? = null,
    /**
     * Идентификатор мерчанта
     */
    val merchantId: String? = null,
    /**
     * Данные для платежа с использованием электронного сертификата
     */
    val certificate: CertificateDetails? = null
) : Bundable, PaymentRequest {

    companion object {
        private const val KEY_PAYMENT_ID = "payment_id"
        private const val KEY_AMOUNT = "amount"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_PAYMENT_INFO = "payment_info"
        private const val KEY_CERT = "certificate"
        private const val KEY_MERCHANT_ID = "merchant_id"

        private val gson = GsonFactory.provide()

        fun fromBundle(bundle: Bundle): CancelRequest {
            return CancelRequest(
                paymentId = bundle.getString(KEY_PAYMENT_ID) ?: "",
                amount = BigDecimal(bundle.getString(KEY_AMOUNT)),
                description = bundle.getString(KEY_DESCRIPTION) ?: "",
                paymentInfo = bundle.getString(KEY_PAYMENT_INFO, null),
                certificate = gson.fromJson<CertificateDetails>(
                    bundle.getString(KEY_CERT),
                    object : TypeToken<CertificateDetails>() {}.type
                ),
                merchantId = bundle.getString(KEY_MERCHANT_ID)
            )
        }
    }

    override val requestType: RequestType
        get() = CANCEL

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(KEY_PAYMENT_ID, paymentId)
            putString(KEY_AMOUNT, amount.toPlainString())
            putString(KEY_DESCRIPTION, description)
            putString(KEY_PAYMENT_INFO, paymentInfo)
            certificate?.let { putString(KEY_CERT, gson.toJson(certificate)) }
            putString(RequestTypeSerialization.KEY, requestType.name)
            putString(KEY_MERCHANT_ID, merchantId)
        }
    }
}