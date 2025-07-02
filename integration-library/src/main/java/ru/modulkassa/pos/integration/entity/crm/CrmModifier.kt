package ru.modulkassa.pos.integration.entity.crm

import java.math.BigDecimal

data class CrmModifier(
    /**
     * Наименование модификатора
     */
    val name: String,
    /**
     * Цена модификатора
     */
    val price: BigDecimal
)
