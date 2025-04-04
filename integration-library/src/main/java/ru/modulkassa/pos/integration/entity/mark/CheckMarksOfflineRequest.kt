package ru.modulkassa.pos.integration.entity.mark

import android.os.Bundle
import com.google.gson.reflect.TypeToken
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.GsonFactory

/**
 * Запрос на проверку марок офлайн
 */
data class CheckMarksOfflineRequest(
    /**
     * Список марок
     */
    val marks: List<Marked>
) : Bundable, MarkRequest {

    companion object {
        private const val KEY_MARKS = "marks"

        private val gson = GsonFactory.provide()

        fun fromBundle(bundle: Bundle): CheckMarksOfflineRequest {
            return CheckMarksOfflineRequest(
                marks = gson.fromJson(
                    bundle.getString(KEY_MARKS),
                    object : TypeToken<List<Marked>>() {}.type
                )
            )
        }
    }

    override val requestType: MarkRequestType
        get() = MarkRequestType.CHECK

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(MarkRequestTypeSerialization.KEY, requestType.name)
            putString(KEY_MARKS, gson.toJson(marks))
        }
    }
}

data class Marked(
    /**
     * Полная марка
     */
    val origin: String,
    /**
     * Обрезанная по алгоритму по формату проверки марка
     */
    val cut: String
)
