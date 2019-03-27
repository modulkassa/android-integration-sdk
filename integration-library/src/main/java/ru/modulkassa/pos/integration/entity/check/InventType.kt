package ru.modulkassa.pos.integration.entity.check

/**
 * Тип товара (признак предмета расчета, тег 1212)
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
    LIGHT_ALCOHOL,
    /**
     * Работа
     */
    JOB,
    /**
     * Ставка азартной игры
     */
    GAMBLING_BET,
    /**
     * Выигрыш азартной игры
     */
    GAMBLING_PRIZE,
    /**
     * Лотерейный билет
     */
    LOTTERY,
    /**
     * Выигрыш лотереи
     */
    LOTTERY_PRIZE,
    /**
     * Предоставление прав на использование результатов интеллектуальной деятельности
     */
    INTELLECTUAL_ACTIVITY,
    /**
     * Платеж
     */
    PAYMENT,
    /**
     * Агентское вознаграждение
     */
    AGENT_COMISSION,
    /**
     * Составной предмет расчета
     */
    COMPOSITE,
    /**
     * Иной предмет расчета
     */
    ANOTHER,
    /**
     * Имущественное право
     */
    PROPERTY_RIGHT,
    /**
     * Внереализационный доход
     */
    NON_OPERATING_GAIN,
    /**
     * Страховые взносы
     */
    INSURANCE_PREMIUM,
    /**
     * Торговый сбор
     */
    SALES_TAX,
    /**
     * Курортный сбор
     */
    RESORT_FEE
}