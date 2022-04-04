package ru.modulkassa.pos.integration.entity.check

/**
 * Запрос с указанием идентификатора документа [Check.id] для получения информации о чеке
 */
data class CheckInfoRequest(

    /**
     * Идентификатор документа [Check.id]
     */
    var checkId: String

)