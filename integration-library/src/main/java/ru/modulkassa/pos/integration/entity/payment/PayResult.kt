package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.payment.PaymentType.CARD

/**
 * Данные результата успешной оплаты
 */
data class PayResult(
    /**
     * Идентификатор платежа, который может понадобиться при его отмене.
     * Для ingenico - это `rrn`, для Яндекс.Кассы это `paymentId`
     */
    val paymentCancelId: String,
    /**
     * Информация от платежной системы, которую необходимо распечатать на чеке
     * Если слипов два, для них используется строка-разделитель Slip.DELIMITER_VALUE
     */
    val slip: List<String>,
    /**
     * Дополнительная информация, которая будет передана при попытке отменить платеж
     */
    val paymentInfo: String? = null,
    /**
     * Тип оплаты
     */
    val paymentType: PaymentType = CARD,
    /**
     * Данные транзакции
     */
    val transactionDetails: TransactionDetails? = null
) : Bundable {

    companion object {
        private const val KEY_CANCEL_ID = "cancel_id"
        private const val KEY_SLIP = "slip"
        private const val KEY_PAYMENT_INFO = "payment_info"
        private const val KEY_PAYMENT_TYPE = "payment_type"

        fun fromBundle(bundle: Bundle): PayResult {
            return PayResult(
                paymentCancelId = bundle.getString(KEY_CANCEL_ID) ?: "",
                slip = bundle.getStringArrayList(KEY_SLIP) ?: arrayListOf(),
                paymentInfo = bundle.getString(KEY_PAYMENT_INFO),
                paymentType = PaymentType.valueOf(bundle.getString(KEY_PAYMENT_TYPE) ?: "CARD"),
                transactionDetails = TransactionDetails.fromBundle(bundle)
            )
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(KEY_CANCEL_ID, paymentCancelId)
            putStringArrayList(KEY_SLIP, ArrayList(slip))
            putString(KEY_PAYMENT_INFO, paymentInfo)
            putString(KEY_PAYMENT_TYPE, paymentType.toString())
            putAll(transactionDetails?.toBundle() ?: Bundle.EMPTY)
            putString(RequestTypeSerialization.KEY, RequestType.PAY.name)
        }
    }

}