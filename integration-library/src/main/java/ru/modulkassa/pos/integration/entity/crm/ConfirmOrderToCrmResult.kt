package ru.modulkassa.pos.integration.entity.crm

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable

/**
 * Результат на подтверждение заказа во внешней crm системе
 */
data class ConfirmOrderToCrmResult(
    val success: Boolean
) : CrmResult, Bundable {
    companion object {
        private const val KEY_SUCCESS = "success"

        fun fromBundle(bundle: Bundle): ConfirmOrderToCrmResult {
            return ConfirmOrderToCrmResult(
                success = bundle.getBoolean(KEY_SUCCESS)
            )
        }
    }

    override val requestType: CrmRequestType
        get() = CrmRequestType.CONFIRM_FISCALED

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(CrmRequestTypeSerialization.KEY, requestType.name)
            putBoolean(KEY_SUCCESS, success)
        }
    }
}
