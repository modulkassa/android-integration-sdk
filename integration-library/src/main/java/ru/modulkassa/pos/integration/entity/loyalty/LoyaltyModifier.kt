package ru.modulkassa.pos.integration.entity.loyalty

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable
import java.math.BigDecimal

/**
 * Модификатор для строки чека в запросе на расчет скидок/бонусов
 * Модификаторы товаров не изменяются внешним сервисом, передаются лишь для информации.
 */
data class LoyaltyModifier(
    /**
     * Идентификатор модификатора
     */
    val id: String,
    /**
     * Наименование
     */
    val name: String = "",
    /**
     * Цена
     */
    val price: BigDecimal = BigDecimal.ZERO,
    /**
     * Количество
     */
    val quantity: BigDecimal = BigDecimal.ZERO
) : Bundable {

    companion object {
        const val NAME_KEY = "modifier/%s/name"
        const val PRICE_KEY = "modifier/%s/price"
        const val QUANTITY_KEY = "modifier/%s/quantity"

        @Throws(LoyaltyInvalidStructureException::class)
        fun fromBundle(id: String, bundle: Bundle): LoyaltyModifier {
            return try {
                LoyaltyModifier(
                    id = id,
                    name = bundle.getString(NAME_KEY.format(id))!!,
                    price = BigDecimal(bundle.getString(PRICE_KEY.format(id))),
                    quantity = BigDecimal(bundle.getString(QUANTITY_KEY.format(id)))
                )
            } catch (e: NullPointerException) {
                throw LoyaltyInvalidStructureException("Отсутствует обязательный параметр", e)
            } catch (e: IllegalStateException) {
                throw LoyaltyInvalidStructureException("Отсутствует обязательный параметр", e)
            } catch (e: NumberFormatException) {
                throw LoyaltyInvalidStructureException("Некорректный формат числового значения", e)
            }
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(NAME_KEY.format(id), name)
            putString(PRICE_KEY.format(id), price.toPlainString())
            putString(QUANTITY_KEY.format(id), quantity.toPlainString())
        }
    }
}