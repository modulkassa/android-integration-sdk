package ru.modulkassa.pos.integration.entity

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.ErrorType.CANCELLED

/**
 * Информация об ошибке
 */
class ResultError(
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
        private const val KEY_MESSAGE = "integration.entity.resulterror.message"
        private const val KEY_TYPE = "integration.entity.resulterror.type"
        private const val KEY_CAUSE = "integration.entity.resulterror.cause"

        fun fromBundle(data: Bundle?): ResultError {
            return if (data == null) {
                ResultError("Запрос был отменен", CANCELLED)
            } else {
                val type = try {
                    ErrorType.valueOf(data.getString(KEY_TYPE, ""))
                } catch (error: IllegalArgumentException) {
                    ErrorType.UNKNOWN
                }
                ResultError(
                    data.getString(KEY_MESSAGE, "Произошла неизвестная ошибка"),
                    type,
                    data.getString(KEY_CAUSE, "")
                )
            }
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(KEY_MESSAGE, message)
            putString(KEY_TYPE, type.toString())
            putString(KEY_CAUSE, cause)
        }
    }
}