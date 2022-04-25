package ru.modulkassa.pos.integration.entity.loyalty

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable
import java.math.BigDecimal

/**
 * Воздействие, примененное к строке чека
 * Воздействие применяется только на основной товар, модификаторы не изменяются.
 * Расчет стоимости модификаторов после применения скидки происходит в приложении МодульКасса.
 */
data class LoyaltyPositionImpact(
    /**
     * Идентификатор воздействия
     * Необходимо обеспечить уникальность значений в рамках ответа. Не может состоять из пробельных символов.
     * Допустимые символы: цифры от 0 до 9, буквы латинского и русского алфавита в любом регистре, специальные символы.
     * Максимальная длина 255 символов.
     */
    val id: String,
    /**
     * Идентификатор позиции из запроса
     * Должен соответствовать одному из ID позиций в запросе.
     * Максимальная длина 255 символов.
     */
    val positionId: String = "",
    /**
     * Итоговая цена позиции с учетом всех скидок от системы лояльности
     * Не должна быть отрицательной.
     */
    val price: BigDecimal = BigDecimal.ZERO,
    /**
     * Количество товара
     * Строго больше 0. Для штучных товаров значение не может быть дробным.
     * Сумма всех quantity для позиций с одним parent_id должна быть равна quantity в изначальной структуре чека.
     * Штучные товары можно делить только целыми штуками.
     * Расчет количества не штучных (весовых) товаров должно быть с точностью до 3 знаков после запятой.
     * Для весовых товаров с модификаторами запрещено разбиение на несколько позиций.
     */
    val quantity: BigDecimal = BigDecimal.ZERO
) : Bundable {

    companion object {
        const val POSITION_ID_KEY = "impact/%s/position_id"
        const val PRICE_KEY = "impact/%s/price"
        const val QUANTITY_KEY = "impact/%s/quantity"

        @Throws(LoyaltyInvalidStructureException::class)
        fun fromBundle(id: String, bundle: Bundle): LoyaltyPositionImpact {
            return try {
                LoyaltyPositionImpact(
                    id = id,
                    positionId = bundle.getString(POSITION_ID_KEY.format(id))!!,
                    price = BigDecimal(bundle.getString(PRICE_KEY.format(id))),
                    quantity = BigDecimal(bundle.getString(QUANTITY_KEY.format(id)))
                )
            } catch (e: NullPointerException) {
                throw LoyaltyInvalidStructureException("Отсутствует обязательный параметр", e)
            } catch (e: NumberFormatException) {
                throw LoyaltyInvalidStructureException("Некорректный формат числового значения", e)
            } catch (e: IllegalStateException) {
                throw LoyaltyInvalidStructureException("Отсутствует обязательный параметр", e)
            }
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(POSITION_ID_KEY.format(id), positionId)
            putString(PRICE_KEY.format(id), price.toPlainString())
            putString(QUANTITY_KEY.format(id), quantity.toPlainString())
        }
    }
}