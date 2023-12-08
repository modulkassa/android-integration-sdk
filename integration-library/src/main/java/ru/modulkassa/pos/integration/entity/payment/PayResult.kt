package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import com.google.gson.reflect.TypeToken
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.GsonFactory
import ru.modulkassa.pos.integration.entity.payment.PaymentType.CARD
import java.math.BigDecimal

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
    val transactionDetails: TransactionDetails? = null,
    /**
     * Полная сумма по транзакции
     */
    val amount: BigDecimal? = null,
    /**
     * Данные для платежа с использованием электронного сертификата
     */
    val certificate: CertificateDetails? = null
) : Bundable {

    companion object {
        private const val KEY_CANCEL_ID = "cancel_id"
        private const val KEY_SLIP = "slip"
        private const val KEY_PAYMENT_INFO = "payment_info"
        private const val KEY_PAYMENT_TYPE = "payment_type"
        private const val KEY_AMOUNT = "amount"
        private const val KEY_CERT = "certificate"
        private val gson = GsonFactory.provide()

        fun fromBundle(bundle: Bundle): PayResult {
            return PayResult(
                paymentCancelId = bundle.getString(KEY_CANCEL_ID) ?: "",
                slip = bundle.getStringArrayList(KEY_SLIP) ?: arrayListOf(),
                paymentInfo = bundle.getString(KEY_PAYMENT_INFO),
                paymentType = PaymentType.valueOf(bundle.getString(KEY_PAYMENT_TYPE) ?: "CARD"),
                transactionDetails = TransactionDetails.fromBundle(bundle),
                amount = bundle.getString(KEY_AMOUNT)?.let { BigDecimal(it) },
                certificate = bundle.getString(KEY_CERT)?.let {
                    gson.fromJson(it, object : TypeToken<CertificateDetails>() {}.type)
                }
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
            amount?.let { putString(KEY_AMOUNT, it.toPlainString()) }
            certificate?.let { putString(KEY_CERT, gson.toJson(certificate)) }
        }
    }

}