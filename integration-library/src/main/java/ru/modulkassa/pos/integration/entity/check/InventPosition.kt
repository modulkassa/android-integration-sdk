package ru.modulkassa.pos.integration.entity.check

import java.math.BigDecimal

/**
 * Позация в чеке
 */
data class InventPosition(
    /**
     * Код товара
     */
    var inventCode: String?,
    /**
     * Наименование товаа
     */
    var name: String,
    /**
     * Цена товара
     */
    var price: BigDecimal,
    /**
     * Количество товаа
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
    var barcode: String? = null
)