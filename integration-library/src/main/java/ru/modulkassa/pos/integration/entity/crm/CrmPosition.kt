package ru.modulkassa.pos.integration.entity.crm

import java.math.BigDecimal

data class CrmPosition(
    /**
     * Идентификатор позиции
     */
    var id: String,
    /**
     * Штрихкод товара
     */
    var barcode: String,
    /**
     * Количество товара
     */
    var quantity: BigDecimal,
    /**
     * Цена товара
     */
    var price: BigDecimal,
    /**
     * Список модификаторов
     */
    val modifiers: List<CrmModifier>,
    /**
     * Данные поставщика
     * Заполняются crm системой
     */
    val supplierInfo: SupplierInfo? = null,
)
