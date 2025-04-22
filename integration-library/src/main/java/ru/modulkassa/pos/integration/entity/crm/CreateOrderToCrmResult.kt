package ru.modulkassa.pos.integration.entity.crm

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable

/**
 * Результат на создание заказа во внешней crm системе
 */
data class CreateOrderToCrmResult(
    /**
     * Идентификатор заказа из внешней системы
     */
    val orderId: Int,
    /**
     * Идентификатор промоутера
     */
    val promouterId: Int? = null,
    /**
     * Номер телефона покупателя, в формате "+7"
     */
    val phone: String,
) : CrmResult, Bundable {

    companion object {
        private const val KEY_ORDER_ID = "orderId"
        private const val KEY_PHONE = "phone"
        private const val KEY_PROMOUTER = "promouterId"

        private const val KET_DEFAULT_VALUE = -999

        fun fromBundle(bundle: Bundle): CreateOrderToCrmResult {
            var promouterId: Int? = bundle.getInt(KEY_PROMOUTER, KET_DEFAULT_VALUE)
            if (promouterId == KET_DEFAULT_VALUE) promouterId = null

            return CreateOrderToCrmResult(
                orderId = bundle.getInt(KEY_ORDER_ID),
                phone = bundle.getString(KEY_PHONE) ?: "",
                promouterId = promouterId
            )
        }
    }

    override val requestType: CrmRequestType
        get() = CrmRequestType.CREATE

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(CrmRequestTypeSerialization.KEY, requestType.name)
            putInt(KEY_ORDER_ID, orderId)
            putString(KEY_PHONE, phone)
            promouterId?.let {
                putInt(KEY_PROMOUTER, it)
            }
        }
    }

}