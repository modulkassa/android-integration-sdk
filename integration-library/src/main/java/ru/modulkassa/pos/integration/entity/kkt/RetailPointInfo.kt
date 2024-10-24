package ru.modulkassa.pos.integration.entity.kkt

/**
 * Информация о торговой точке
 */
data class RetailPointInfo(
    /**
     * Уникальный идентификатор в рамках Модулькассы
     */
    val id: String,
    /**
     * Название торговой точки
     */
    val name: String,
    /**
     * Адрес торговой точки
     */
    val address: String,
    /**
     * ИНН торговой точки
     */
    val inn: String
)