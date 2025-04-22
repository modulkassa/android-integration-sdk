package ru.modulkassa.pos.integration.entity.crm

/**
 * Тип запроса к внешней crm системе
 */
enum class CrmRequestType {
    /**
     * Создать заказ
     */
    CREATE,
    /**
     * Подтвердить факт фискализации
     */
    CONFIRM_FISCALED
}

/**
 * Параметры сериализации для типа запроса
 */
object CrmRequestTypeSerialization {
    const val KEY = "request_type"
}