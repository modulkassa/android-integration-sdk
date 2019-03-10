package ru.modulkassa.pos.integration.entity.check

/**
 * Признак агента (тег 1057)
 */
enum class AgentTag {
    /**
     * Банковский платежный агент
     */
    BANK_PAY_AGENT,
    /**
     * Банковский платежный субагент
     */
    BANK_PAY_SUBAGENT,
    /**
     * Платежный агент
     */
    PAY_AGENT,
    /**
     * Платежный субагент
     */
    PAY_SUBAGENT,
    /**
     * Поверенный
     */
    ATTORNEY,
    /**
     * Комиссионер
     */
    COMMISSION_AGENT,
    /**
     * Иной агент
     */
    AGENT
}