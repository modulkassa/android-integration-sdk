package ru.modulkassa.pos.integration.entity.loyalty

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable

/**
 * Запрос на расчет скидок/бонусов
 */
data class LoyaltyRequest(
    /**
     * Идентификатор чека
     */
    val id: String,
    /**
     * Идентификатор торговой точки
     */
    val retailPointId: String = "",
    /**
     * Идентификатор кассира
     */
    val userId: String = "",
    /**
     * Список товаров
     */
    val positions: List<LoyaltyPosition> = emptyList()
) : Bundable {

    companion object {
        const val REQUEST_ID_KEY = "request_id"
        const val RETAIL_POINT_ID_KEY = "retail_point_id"
        const val USER_ID_KEY = "user_id"
        const val POSITIONS_KEY = "positions"

        @Throws(LoyaltyInvalidStructureException::class)
        fun fromBundle(bundle: Bundle): LoyaltyRequest {
            return try {
                LoyaltyRequest(
                    id = bundle.getString(REQUEST_ID_KEY)!!,
                    retailPointId = bundle.getString(RETAIL_POINT_ID_KEY)!!,
                    userId = bundle.getString(USER_ID_KEY)!!,
                    positions = (bundle.getStringArrayList(POSITIONS_KEY) ?: arrayListOf())
                        .map { LoyaltyPosition.fromBundle(it, bundle) }
                )
            } catch (e: IllegalStateException) {
                throw LoyaltyInvalidStructureException("Отсутствует обязательный параметр", e)
            } catch (e: NullPointerException) {
                throw LoyaltyInvalidStructureException("Отсутствует обязательный параметр", e)
            }
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(REQUEST_ID_KEY, id)
            putString(RETAIL_POINT_ID_KEY, retailPointId)
            putString(USER_ID_KEY, userId)

            putStringArrayList(POSITIONS_KEY, ArrayList(positions.map { item -> item.id }))
            positions.forEach { putAll(it.toBundle()) }
        }
    }
}

