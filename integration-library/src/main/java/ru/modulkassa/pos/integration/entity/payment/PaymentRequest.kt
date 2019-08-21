package ru.modulkassa.pos.integration.entity.payment

interface PaymentRequest {
    /**
     * Тип запроса к платежной системе
     */
    val requestType: RequestType
}