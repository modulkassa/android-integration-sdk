package ru.modulkassa.pos.integration.entity.check

/**
 * Информация о платежных агентах, которая устанавливается на уровне чека
 */
data class CheckLevelAgentInformation(
    /**
     * Признак агента (тег 1057)
     */
    val tags: List<AgentTag>? = null,
    /**
     * Телефон платежного агента (тег 1073)
     */
    val paymentAgentPhone: String? = null,
    /**
     * Телефон оператора по приему платежей (тег 1074)
     */
    val commissionAgentPhone: String? = null,
    /**
     * Телефон поставщика (тег 1171)
     */
    val contractorPhone: String? = null,
    /**
     * Адрес оператора перевода (тег 1005)
     */
    val transferOperatorAddress: String? = null,
    /**
     * ИНН оператора перевода (тег 1016)
     */
    val transferOperatorInn: String? = null,
    /**
     * Операция платежного агента (тег 1044)
     */
    val operation: String? = null,
    /**
     * Телефон оператора перевода (тег 1075)
     */
    val transferOperatorPhone: String? = null
)