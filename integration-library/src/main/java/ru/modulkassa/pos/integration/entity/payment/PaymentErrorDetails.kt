package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable

/**
 * Данные результата неудачной оплаты / возврата / отмены
 */
data class PaymentErrorDetails(
    /**
     * Информация от платежной системы, которую необходимо распечатать на чеке
     */
    val slip: List<String>,
    /**
     * Текст сообщения для пользователя
     */
    val message: String = "",
    /**
     * Код ответа платежного модуля
     */
    val responseCode: String = PLACEHOLDER,
    /**
     * Код ответа хоста
     */
    val hostAnswerCode: String = PLACEHOLDER,
    /**
     * Код ответа эмитента
     */
    val issuerAnswerCode: String = PLACEHOLDER
) : Bundable {

    companion object {
        private const val KEY_SLIP = "slip"
        private const val KEY_MESSAGE = "error_message"
        private const val KEY_RESPONSE_CODE = "response_code"
        private const val KEY_HOST_ANSWER_CODE = "host_answer_code"
        private const val KEY_ISSUER_ANSWER_CODE = "issuer_answer_code"
        private const val PLACEHOLDER = "---"

        fun fromBundle(bundle: Bundle): PaymentErrorDetails {

            return PaymentErrorDetails(
                slip = bundle.getStringArrayList(KEY_SLIP) ?: listOf(),
                message = bundle.getString(KEY_MESSAGE) ?: "",
                responseCode = bundle.getString(KEY_RESPONSE_CODE, PLACEHOLDER),
                hostAnswerCode = bundle.getString(KEY_HOST_ANSWER_CODE, PLACEHOLDER),
                issuerAnswerCode = bundle.getString(KEY_ISSUER_ANSWER_CODE, PLACEHOLDER)
            )
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putStringArrayList(KEY_SLIP, ArrayList(slip))
            putString(KEY_MESSAGE, message)
            putString(KEY_RESPONSE_CODE, responseCode)
            putString(KEY_HOST_ANSWER_CODE, hostAnswerCode)
            putString(KEY_ISSUER_ANSWER_CODE, issuerAnswerCode)
        }
    }

}