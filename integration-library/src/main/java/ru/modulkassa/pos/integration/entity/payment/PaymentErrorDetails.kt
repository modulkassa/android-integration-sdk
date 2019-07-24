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
    val message: String = ""
) : Bundable {

    companion object {
        private const val KEY_SLIP = "slip"
        private const val KEY_MESSAGE = "error_message"

        fun fromBundle(bundle: Bundle): PaymentErrorDetails {
            return PaymentErrorDetails(
                slip = bundle.getStringArrayList(KEY_SLIP) ?: listOf(),
                message = bundle.getString(KEY_MESSAGE) ?: ""
            )
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putStringArrayList(KEY_SLIP, ArrayList(slip))
            putString(KEY_MESSAGE, message)
        }
    }

}