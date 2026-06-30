package ru.modulkassa.pos.integration.entity.off_qr

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.ErrorType
import ru.modulkassa.pos.integration.entity.GsonFactory

/**
 * Результат ошибки офлайн оплаты
 */
class OffQrResultError(
    /**
     * Идентификатор документа
     */
    val id: String,
    /**
     * Идентификатор заказа/заявки
     */
    val orderId: String,

    /**
     * Описание ошибки
     */
    val message: String,
    /**
     * Тип ошибки
     */
    val type: ErrorType,
    /**
     * Причина ошибки
     */
    val cause: String = ""
) : Bundable {

    companion object {
        const val KEY_SERIALIZED_OFF_QR_ERROR = "integration.entity.off_qr_error"
        private val gson = GsonFactory.provide()

        fun fromBundle(data: Bundle): OffQrResultError? {
            return gson.fromJson(data.getString(KEY_SERIALIZED_OFF_QR_ERROR), OffQrResultError::class.java)
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().also {
            val serialized = gson.toJson(this)
            it.putString(KEY_SERIALIZED_OFF_QR_ERROR, serialized)
        }
    }
}