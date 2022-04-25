package ru.modulkassa.pos.integration.entity.loyalty

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.check.InventType
import ru.modulkassa.pos.integration.entity.check.InventType.INVENTORY
import ru.modulkassa.pos.integration.entity.check.Measure
import ru.modulkassa.pos.integration.entity.check.Measure.PCS
import java.math.BigDecimal

/**
 * Строка чека в запросе на расчет скидок/бонусов
 */
data class LoyaltyPosition(
    /**
     * Идентификатор строки в чеке
     */
    val id: String,
    /**
     * Код товара
     */
    val inventCode: String = "",
    /**
     * Штрихкод товара
     */
    val barcode: String = "",
    /**
     * Наименование
     */
    val name: String = "",
    /**
     * Тип товара
     */
    val type: InventType = INVENTORY,
    /**
     * Единица измерения
     */
    val measure: Measure = PCS,
    /**
     * Цена товара (с учетом цен примененных модификаторов) после применения скидок МодульКассы
     */
    val price: BigDecimal = BigDecimal.ZERO,
    /**
     * Количество
     */
    val quantity: BigDecimal = BigDecimal.ZERO,
    /**
     * Примененные модификаторы
     */
    val modifiers: List<LoyaltyModifier> = listOf()
) : Bundable {

    companion object {
        const val INVENT_CODE_KEY = "position/%s/invent_code"
        const val BARCODE_KEY = "position/%s/barcode"
        const val NAME_KEY = "position/%s/name"
        const val TYPE_KEY = "position/%s/type"
        const val MEASURE_KEY = "position/%s/measure"
        const val PRICE_KEY = "position/%s/price"
        const val QUANTITY_KEY = "position/%s/quantity"
        const val MODIFIERS_KEY = "position/%s/modifiers"

        @Throws(LoyaltyInvalidStructureException::class)
        fun fromBundle(id: String, bundle: Bundle): LoyaltyPosition {
            return try {
                LoyaltyPosition(
                    id = id,
                    inventCode = bundle.getString(INVENT_CODE_KEY.format(id))!!,
                    barcode = bundle.getString(BARCODE_KEY.format(id))!!,
                    name = bundle.getString(NAME_KEY.format(id))!!,
                    type = InventType.valueOf(bundle.getString(TYPE_KEY.format(id))!!),
                    measure = Measure.valueOf(bundle.getString(MEASURE_KEY.format(id))!!),
                    price = BigDecimal(bundle.getString(PRICE_KEY.format(id))),
                    quantity = BigDecimal(bundle.getString(QUANTITY_KEY.format(id))),
                    modifiers = (bundle.getStringArrayList(MODIFIERS_KEY.format(id)) ?: arrayListOf())
                        .map { LoyaltyModifier.fromBundle(it, bundle) }
                )
            } catch (e: NullPointerException) {
                throw LoyaltyInvalidStructureException("Отсутствует обязательный параметр", e)
            } catch (e: NumberFormatException) {
                throw LoyaltyInvalidStructureException("Некорректный формат числового значения", e)
            } catch (e: IllegalArgumentException) {
                throw LoyaltyInvalidStructureException("Некорректное значение", e)
            } catch (e: IllegalStateException) {
                throw LoyaltyInvalidStructureException("Отсутствует обязательный параметр", e)
            }
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(INVENT_CODE_KEY.format(id), inventCode)
            putString(BARCODE_KEY.format(id), barcode)
            putString(NAME_KEY.format(id), name)
            putString(TYPE_KEY.format(id), type.name)
            putString(MEASURE_KEY.format(id), measure.name)
            putString(PRICE_KEY.format(id), price.toPlainString())
            putString(QUANTITY_KEY.format(id), quantity.toPlainString())

            putStringArrayList(MODIFIERS_KEY.format(id), ArrayList(modifiers.map { it.id }))
            modifiers.forEach { putAll(it.toBundle()) }
        }
    }
}