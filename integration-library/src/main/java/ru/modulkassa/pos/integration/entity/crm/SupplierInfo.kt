package ru.modulkassa.pos.integration.entity.crm

/**
 * Данные поставщика
 */
data class SupplierInfo(
    /**
     * Номер телефона
     */
    val phone: String? = null,
    /**
     * Название
     */
    val name: String? = null,
    /**
     * ИНН
     */
    val inn: String? = null,
)
