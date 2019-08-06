package ru.modulkassa.pos.integration.entity.check

/**
 * Признак агента (тег 1057)
 */
enum class AgentTag(
    /**
     * Код агента из ОФД
     */
    val code: Int
) {
    /**
     * Банковский платежный агент
     */
    BANK_PAY_AGENT(1),
    /**
     * Банковский платежный субагент
     */
    BANK_PAY_SUBAGENT(2),
    /**
     * Платежный агент
     */
    PAY_AGENT(4),
    /**
     * Платежный субагент
     */
    PAY_SUBAGENT(8),
    /**
     * Поверенный
     */
    ATTORNEY(16),
    /**
     * Комиссионер
     */
    COMMISSION_AGENT(32),
    /**
     * Иной агент
     */
    AGENT(64);

    companion object {
        /**
         * Конвертирует [value] в список агентских тегов
         */
        fun convertToAgentTags(value: Int): List<AgentTag> {
            val agentTags = ArrayList<AgentTag>()
            AgentTag.values().forEach { tag ->
                if (tag.code and value == tag.code) {
                    agentTags.add(tag)
                }
            }
            return agentTags
        }
    }
}