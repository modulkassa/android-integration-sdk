package ru.modulkassa.pos.integration.entity.check

import ru.modulkassa.pos.integration.entity.check.InventType.INVENTORY
import java.math.BigDecimal

/**
 * Строка в чеке
 */
data class InventPosition(
    /**
     * Код товара
     */
    var inventCode: String?,
    /**
     * Наименование товара
     */
    var name: String,
    /**
     * Цена товара
     * Точность должна быть указана до 2х знаков [BigDecimal.setScale(2, BigDecimal.ROUND_DOWN)]
     */
    var price: BigDecimal,
    /**
     * Количество товара
     * Точность должна быть указана до 3х знаков [BigDecimal.setScale(3, BigDecimal.ROUND_DOWN)]
     */
    var quantity: BigDecimal,
    /**
     * Тег налоговой ставки
     */
    var vatTag: VatTag,
    /**
     * Единица измерения товара
     */
    var measure: Measure,
    /**
     * Штрихкод товара
     */
    var barcode: String? = null,
    /**
     * Тип товара
     */
    var inventType: InventType? = INVENTORY
)