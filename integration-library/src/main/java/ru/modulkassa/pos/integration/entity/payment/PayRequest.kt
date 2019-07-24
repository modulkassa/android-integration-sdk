package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.payment.RequestType.PAY
import java.math.BigDecimal

/**
 * Информация о платеже, который необходимо провести
 */
data class PayRequest(
    /**
     * Идентификатор чека
     */
    val checkId: String,
    /**
     * Сумма к оплате
     */
    val amount: BigDecimal,
    /**
     * Описание платежа
     */
    val description: String,
    /**
     * Идентификатор мерчанта
     */
    val merchantId: String? = null
) : Bundable, PaymentRequest {

    companion object {
        private const val KEY_CHECK_ID = "check_id"
        private const val KEY_AMOUNT = "amount"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_MERCHANT_ID = "merchant_id"

        fun fromBundle(bundle: Bundle): PayRequest {
            return PayRequest(
                checkId = bundle.getString(KEY_CHECK_ID, ""),
                amount = BigDecimal(bundle.getString(KEY_AMOUNT, "0")),
                description = bundle.getString(KEY_DESCRIPTION, ""),
                merchantId =  bundle.getString(KEY_MERCHANT_ID)
            )
        }
    }

    override val requestType: RequestType
        get() = PAY

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(KEY_CHECK_ID, checkId)
            putString(KEY_AMOUNT, amount.toPlainString())
            putString(KEY_DESCRIPTION, description)
            putString(KEY_MERCHANT_ID, merchantId)
            putString(RequestTypeSerialization.KEY, requestType.name)
        }
    }

}