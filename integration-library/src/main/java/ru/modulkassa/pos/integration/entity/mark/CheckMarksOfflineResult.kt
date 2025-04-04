package ru.modulkassa.pos.integration.entity.mark

import android.os.Bundle
import com.google.gson.reflect.TypeToken
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.GsonFactory

data class CheckMarksOfflineResult(
    /**
     * Список результатов проверок марок
     */
    val marks: List<MarkOfflineResult>
) : Bundable {

    companion object {
        private const val KEY_MARKS = "marks"

        private val gson = GsonFactory.provide()

        fun fromBundle(bundle: Bundle): CheckMarksOfflineResult {
            return CheckMarksOfflineResult(
                marks = gson.fromJson(
                    bundle.getString(KEY_MARKS),
                    object : TypeToken<List<MarkOfflineResult>>() {}.type
                )
            )
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(KEY_MARKS, gson.toJson(marks))
        }
    }
}

data class MarkOfflineResult(
    /**
     * Полная марка
     */
    val origin: String,
    /**
     * Статус проверки марки
     */
    val status: MarkOfflineStatus,
    /**
     * Уникальный идентификатор квитанции
     */
    val reqId: String,
    /**
     * Дата и время формирования запроса
     */
    val reqTimestamp: Long,
    /**
     * Сопроводительный текст
     */
    val message: String? = null,
    // todo добавить inst ЛМ?
)

enum class MarkOfflineStatus {
    /**
     * Используется при положительном результате проверки
     */
    SUCCESS,

    /**
     * Используется, если не удалось проверить
     */
    UNKNOWN,

    /**
     * Используется при отрицательном результате проверки
     */
    FAILED;
}