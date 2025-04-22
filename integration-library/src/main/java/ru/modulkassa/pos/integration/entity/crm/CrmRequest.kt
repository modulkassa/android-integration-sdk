package ru.modulkassa.pos.integration.entity.crm

/**
 * Запрос к внешней crm системе
 */
interface CrmRequest {
    /**
     * Тип запроса к внешней crm системе
     */
    val requestType: CrmRequestType
}
