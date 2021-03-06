package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable
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
    val paymentInfo: String? = null
) : Bundable, PaymentRequest {

    companion object {
        private const val KEY_PAYMENT_ID = "payment_id"
        private const val KEY_AMOUNT = "amount"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_PAYMENT_INFO = "payment_info"

        fun fromBundle(bundle: Bundle): CancelRequest {
            return CancelRequest(
                paymentId = bundle.getString(KEY_PAYMENT_ID) ?: "",
                amount = BigDecimal(bundle.getString(KEY_AMOUNT)),
                description = bundle.getString(KEY_DESCRIPTION) ?: "",
                paymentInfo = bundle.getString(KEY_PAYMENT_INFO, null)
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
            putString(RequestTypeSerialization.KEY, requestType.name)
        }
    }
}