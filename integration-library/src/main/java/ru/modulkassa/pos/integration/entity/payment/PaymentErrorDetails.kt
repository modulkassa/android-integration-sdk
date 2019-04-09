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
    val slip: List<String>
) : Bundable {

    companion object {
        private const val KEY_SLIP = "slip"

        fun fromBundle(bundle: Bundle?): PaymentErrorDetails {
            val slip: List<String> =
                if (bundle != null) {
                    bundle.getStringArrayList(KEY_SLIP) ?: listOf()
                } else {
                    listOf()
                }
            return PaymentErrorDetails(slip)
        }
    }

    override fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putStringArrayList(KEY_SLIP, ArrayList(slip))
        return bundle
    }

}