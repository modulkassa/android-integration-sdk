package ru.modulkassa.pos.integration.entity.check

/**
 * Системы налогообложения
 */
enum class TaxationMode {
    /**
     * ОСН
     */
    COMMON,
    /**
     * УСН доход
     */
    SIMPLIFIED,
    /**
     * УСН доход-расход
     */
    SIMPLIFIED_WITH_EXPENSE,
    /**
     * ЕСХН (сельхозналог)
     */
    COMMON_AGRICULTURAL,
    /**
     * ПСН (патент)
     */
    PATENT
}