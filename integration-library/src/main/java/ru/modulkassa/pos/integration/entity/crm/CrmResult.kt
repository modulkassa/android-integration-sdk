package ru.modulkassa.pos.integration.entity.crm

/**
 * Ответ от внешней crm системы
 */
interface CrmResult {
    /**
     * Тип ответа от внешней crm системы
     */
    val requestType: CrmRequestType
}
