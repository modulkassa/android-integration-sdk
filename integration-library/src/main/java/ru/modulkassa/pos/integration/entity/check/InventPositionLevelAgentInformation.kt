package ru.modulkassa.pos.integration.entity.check

/**
 * Информация о платежных агентах, которая устанавливается на уровне позиции (предмета расчета)
 */
data class InventPositionLevelAgentInformation(
    /**
     * Признак платежного агента (тег 1222)
     */
    val tags: List<AgentTag>? = null,
    /**
     * ИНН поставщика (тег 1226)
     */
    val contractorInn: String? = null,
    /**
     * Телефон поставщика (тег 1171)
     */
    val contractorPhone: String? = null,
    /**
     * Наименование поставщика (тег 1225)
     */
    val contractorName: String? = null,
    /**
     * Телефон оператора перевода (тег 1075)
     */
    val transferOperatorPhone: String? = null,
    /**
     * Операция платежного агента (тег 1044)
     */
    val operation: String? = null,
    /**
     * Телефон платежного агента (тег 1073)
     */
    val phone: String? = null,
    /**
     * Телефон оператора по приему платежей (тег 1074)
     */
    val commissionAgentPhone: String? = null,
    /**
     * Наименование оператора перевода (тег 1026)
     */
    val transferOperatorName: String? = null,
    /**
     * Адрес оператора перевода (тег 1005)
     */
    val transferOperatorAddress: String? = null,
    /**
     * ИНН оператора перевода (тег 1016)
     */
    val transferOperatorInn: String? = null
)