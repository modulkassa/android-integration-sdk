package ru.modulkassa.pos.integration.entity.off_qr

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.GsonFactory
import ru.modulkassa.pos.integration.entity.check.MoneyPosition

/**
 * Результат успешной офлайн оплаты
 */
data class OffQrResultSuccess(
    /**
     * Идентификатор документа
     */
    val id: String,
    /**
     * Идентификатор заказа/заявки
     */
    val orderId: String,
    /**
     * Данные проведенного платежа с указанием
     * - ID ссылки (QR СБП) в [linkedId]
     * - дата и время платежа в [operationDateTime]
     * - сумма операции в [sum]
     */
    val paymentDetails: MoneyPosition
) : Bundable {

    companion object {
        const val KEY_SERIALIZED_OFF_QR_RESULT = "integration.entity.off_qr_success"
        private val gson = GsonFactory.provide()

        fun fromBundle(data: Bundle): OffQrResultSuccess? {
            return gson.fromJson(data.getString(KEY_SERIALIZED_OFF_QR_RESULT), OffQrResultSuccess::class.java)
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().also {
            val serialized = gson.toJson(this)
            it.putString(KEY_SERIALIZED_OFF_QR_RESULT, serialized)
        }
    }
}