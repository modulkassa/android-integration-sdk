package ru.modulkassa.pos.integration.entity.mark

interface MarkRequest {
    /**
     * Тип запроса к офлайн системе обработки марок
     */
    val requestType: MarkRequestType
}