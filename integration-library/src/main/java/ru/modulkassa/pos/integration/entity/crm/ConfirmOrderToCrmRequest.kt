package ru.modulkassa.pos.integration.entity.crm

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable

/**
 * Запрос на подтверждение заказа во внешней crm системе
 */
data class ConfirmOrderToCrmRequest(
    /**
     * Идентификатор заказа из внешней системы
     */
    val orderId: Int,
    /**
     * Номер телефона покупателя, в формате "+7"
     */
    val phone: String
) : Bundable, CrmRequest {

    companion object {
        private const val KEY_ORDER_ID = "orderId"
        private const val KEY_PHONE = "phone"

        fun fromBundle(bundle: Bundle): ConfirmOrderToCrmRequest {
            return ConfirmOrderToCrmRequest(
                orderId = bundle.getInt(KEY_ORDER_ID),
                phone = bundle.getString(KEY_PHONE) ?: ""
            )
        }
    }

    override val requestType: CrmRequestType
        get() = CrmRequestType.CONFIRM_FISCALED

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(CrmRequestTypeSerialization.KEY, requestType.name)
            putInt(KEY_ORDER_ID, orderId)
            putString(KEY_PHONE, phone)
        }
    }
}
