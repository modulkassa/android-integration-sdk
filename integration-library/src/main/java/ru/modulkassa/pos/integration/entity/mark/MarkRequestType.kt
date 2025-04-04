package ru.modulkassa.pos.integration.entity.mark

/**
 * Тип запроса к офлайн системе обработки марок
 */
enum class MarkRequestType {
    /**
     * Проверить марку
     */
    CHECK
}

/**
 * Параметры сериализации для типа запроса
 */
object MarkRequestTypeSerialization {
    const val KEY = "request_type"
}