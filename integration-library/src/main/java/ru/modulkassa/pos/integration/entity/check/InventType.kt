package ru.modulkassa.pos.integration.entity.check

/**
 * Тип товара
 */
enum class InventType {
    /**
     * Товар из справочника
     */
    INVENTORY,
    /**
     * Предоплата(Аванс)
     */
    PREPAID,
    /**
     * Корректировка предоплаты
     */
    PREPAID_CORRECTION,
    /**
     * Табачная продукция
     */
    TOBACCO,
    /**
     * Услуга
     */
    SERVICE,
    /**
     * Прочий акцизный товар.
     */
    OTHER_EXCISABLE,
    /**
     * Акцизный алкоголь.
     */
    ALCOHOL,
    /**
     * Слабый алкоголь. Обрабатывается как обычный товар
     */
    LIGHT_ALCOHOL
}