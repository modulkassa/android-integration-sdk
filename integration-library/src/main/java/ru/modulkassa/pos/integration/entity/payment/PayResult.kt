package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.payment.PaymentType.CARD

/**
 * Данные результата успешной оплаты
 */
data class PayResult(
    /**
     * Идентификатор платежа, которые может понадобиться при его отмене.
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
     * Код авторизации
     */
    val authorizationCode: String? = null,
    /**
     * Номер транзакции
     */
    val transactionNumber: String? = null,
    /**
     * Маскированный номер карты
     */
    val maskedCardNumber: String? = null,
    /**
     * Дата окончания срока действия карты
     */
    val cardExpiryDate: String? = null,
    /**
     * Дата и время транзакции в формате ISO8601
     */
    val operationDateTime: String? = null,
    /**
     * Номер транзакции
     */
    val terminalNumber: String? = null,
    /**
     * Название платежной системы
     */
    val paymentSystemName: String? = null

) : Bundable {

    companion object {
        private const val KEY_CANCEL_ID = "cancel_id"
        private const val KEY_SLIP = "slip"
        private const val KEY_PAYMENT_INFO = "payment_info"
        private const val KEY_PAYMENT_TYPE = "payment_type"
        private const val KEY_AUTHORIZATION_CODE = "authorization_code"
        private const val KEY_TRANSACTION_NUMBER = "transaction_number"
        private const val KEY_MASKED_CARD_NUMBER = "masked_card_number"
        private const val KEY_CARD_EXPIRY_DATE = "card_expiry_date"
        private const val KEY_OPERATION_DATETIME = "operation_datetime"
        private const val KEY_TERMINAL_NUMBER = "terminal_number"
        private const val KEY_PAYMENT_SYSTEM_NAME = "payment_system_name"

        fun fromBundle(bundle: Bundle): PayResult {
            return PayResult(
                paymentCancelId = bundle.getString(KEY_CANCEL_ID) ?: "",
                slip = bundle.getStringArrayList(KEY_SLIP) ?: arrayListOf(),
                paymentInfo = bundle.getString(KEY_PAYMENT_INFO),
                paymentType = PaymentType.valueOf(bundle.getString(KEY_PAYMENT_TYPE) ?: "CARD"),
                authorizationCode = bundle.getString(KEY_AUTHORIZATION_CODE),
                transactionNumber = bundle.getString(KEY_TRANSACTION_NUMBER),
                maskedCardNumber = bundle.getString(KEY_MASKED_CARD_NUMBER),
                cardExpiryDate = bundle.getString(KEY_CARD_EXPIRY_DATE),
                operationDateTime = bundle.getString(KEY_OPERATION_DATETIME),
                terminalNumber = bundle.getString(KEY_TERMINAL_NUMBER),
                paymentSystemName = bundle.getString(KEY_PAYMENT_SYSTEM_NAME)
            )
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(KEY_CANCEL_ID, paymentCancelId)
            putStringArrayList(KEY_SLIP, ArrayList(slip))
            putString(KEY_PAYMENT_INFO, paymentInfo)
            putString(KEY_PAYMENT_TYPE, paymentType.toString())
            putString(KEY_AUTHORIZATION_CODE, authorizationCode)
            putString(KEY_TRANSACTION_NUMBER, transactionNumber)
            putString(KEY_MASKED_CARD_NUMBER, maskedCardNumber)
            putString(KEY_CARD_EXPIRY_DATE, cardExpiryDate)
            putString(KEY_OPERATION_DATETIME, operationDateTime)
            putString(KEY_TERMINAL_NUMBER, terminalNumber)
            putString(KEY_PAYMENT_SYSTEM_NAME, paymentSystemName)
        }
    }

}