package ru.modulkassa.pos.integration.entity.kkt

/**
 * Информация о ККТ
 */
data class KktDescription(
    /**
     * регистрационный номер ККТ
     */
    val registrationNumber: String?,
    /**
     * Название модели
     */
    val modelName: String,
    /**
     * Заводской номер ККТ
     */
    val manufacturerSerialNumber: String?,
    /**
     * Номер ФН
     */
    val fnNumber: String?,

    /**
     * Данные торговой точки, если она уже активирована.
     * Если торговая точка еще не была активирована, то вернется null
     */
    val retailPointInfo: RetailPointInfo? = null,
    /**
     * Версия ФФД ККТ - тег 1189
     */
    val ffdKktVersion: String? = null,
    /**
     * Версия ФФД ФН - тег 1190
     */
    val ffdFnVersion: String? = null
)