package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable

/**
 * Данные результата успешной отмены
 */
data class CancelResult(
    /**
     * Информация от платежной системы, которую необходимо распечатать на чеке
     * Если слипов два, для них используется строка-разделитель Slip.DELIMITER_VALUE
     */
    val slip: List<String>,
    /**
     * Данные транзакции
     */
    val transactionDetails: TransactionDetails? = null
) : Bundable {

    companion object {
        private const val KEY_SLIP = "slip"

        fun fromBundle(bundle: Bundle): CancelResult {
            return CancelResult(
                slip = bundle.getStringArrayList(KEY_SLIP) ?: arrayListOf(),
                transactionDetails = TransactionDetails.fromBundle(bundle)
            )
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putStringArrayList(KEY_SLIP, ArrayList(slip))
            putAll(transactionDetails?.toBundle() ?: Bundle.EMPTY)
            putString(RequestTypeSerialization.KEY, RequestType.CANCEL.name)
        }
    }
}