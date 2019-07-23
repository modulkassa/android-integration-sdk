package ru.modulkassa.pos.integration.entity.check

/**
 * Данные покупателя (например, для страхового полиса или расчета между юр. лицами)
 */
data class ClientInformation(
    /**
     * ФИО покупателя
     */
    val name: String?,
    /**
     * Серия, номер паспорта
     */
    val documentNumber: String?,
    /**
     * ИНН покупателя
     */
    val inn: String?
)