package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable

/**
 * Данные транзакции оплаты/возврата
 */
data class TransactionDetails(
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
     * Номер терминала
     */
    val terminalNumber: String? = null,
    /**
     * Название платежной системы
     */
    val paymentSystemName: String? = null
) : Bundable {

    companion object {
        private const val KEY_PREFFIX = "transaction_details"
        private const val KEY_AUTHORIZATION_CODE = "authorization_code"
        private const val KEY_TRANSACTION_NUMBER = "transaction_number"
        private const val KEY_MASKED_CARD_NUMBER = "masked_card_number"
        private const val KEY_CARD_EXPIRY_DATE = "card_expiry_date"
        private const val KEY_OPERATION_DATETIME = "operation_datetime"
        private const val KEY_TERMINAL_NUMBER = "terminal_number"
        private const val KEY_PAYMENT_SYSTEM_NAME = "payment_system_name"

        fun fromBundle(bundle: Bundle): TransactionDetails? {
            return if (bundle.keySet().any { it.startsWith(KEY_PREFFIX) }) {
                TransactionDetails(
                    authorizationCode = bundle.getString("$KEY_PREFFIX/$KEY_AUTHORIZATION_CODE"),
                    transactionNumber = bundle.getString("$KEY_PREFFIX/$KEY_TRANSACTION_NUMBER"),
                    maskedCardNumber = bundle.getString("$KEY_PREFFIX/$KEY_MASKED_CARD_NUMBER"),
                    cardExpiryDate = bundle.getString("$KEY_PREFFIX/$KEY_CARD_EXPIRY_DATE"),
                    operationDateTime = bundle.getString("$KEY_PREFFIX/$KEY_OPERATION_DATETIME"),
                    terminalNumber = bundle.getString("$KEY_PREFFIX/$KEY_TERMINAL_NUMBER"),
                    paymentSystemName = bundle.getString("$KEY_PREFFIX/$KEY_PAYMENT_SYSTEM_NAME")
                )
            } else {
                null
            }
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString("$KEY_PREFFIX/$KEY_AUTHORIZATION_CODE", authorizationCode)
            putString("$KEY_PREFFIX/$KEY_TRANSACTION_NUMBER", transactionNumber)
            putString("$KEY_PREFFIX/$KEY_MASKED_CARD_NUMBER", maskedCardNumber)
            putString("$KEY_PREFFIX/$KEY_CARD_EXPIRY_DATE", cardExpiryDate)
            putString("$KEY_PREFFIX/$KEY_OPERATION_DATETIME", operationDateTime)
            putString("$KEY_PREFFIX/$KEY_TERMINAL_NUMBER", terminalNumber)
            putString("$KEY_PREFFIX/$KEY_PAYMENT_SYSTEM_NAME", paymentSystemName)
        }
    }
}