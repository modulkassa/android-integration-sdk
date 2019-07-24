package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable

/**
 * Результат успешного выполнения сверки итогов
 */
data class ReconciliationResult(
    /**
     * Информация от платежной системы, которую необходимо распечатать на чеке
     */
    val slip: List<String>
) : Bundable {

    companion object {
        private const val KEY_SLIP = "slip"

        fun fromBundle(bundle: Bundle): ReconciliationResult {
            return ReconciliationResult(
                slip = bundle.getStringArrayList(KEY_SLIP) ?: arrayListOf()
            )
        }

    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putStringArrayList(KEY_SLIP, ArrayList(slip))
            putString(RequestTypeSerialization.KEY, RequestType.RECONCILIATION.name)
        }
    }
}