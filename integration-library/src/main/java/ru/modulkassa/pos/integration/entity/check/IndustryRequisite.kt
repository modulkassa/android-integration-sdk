package ru.modulkassa.pos.integration.entity.check

/**
 * Используется для указания отраслевого реквизита как на уровне чека (Тег 1261), так и на уровне позиции (Тег 1260)
 */
data class IndustryRequisite(
    /**
     * Идентификатор ФОИВ (тег 1262)
     */
    val foivId: String,
    /**
     * Дата документа основания в формате ISO 8601 (тег 1263)
     */
    val docDate: String,
    /**
     * Номер документа основания (тег 1264)
     */
    val docNumber: String,
    /**
     * Значение отраслевого реквизита (тег 1265)
     */
    val docData: String
) {

    companion object {
        /**
         * Заполнить отраслевой реквизит уровня позиции по результатам проверки марки в системе Честный знак
         *
         * @param reqId - Уникальный идентификатор UUID запроса в систему Честный знак
         * Пример: "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee"
         * @param reqTimestamp - Дата и время с точностью до миллисекунд формирования запроса в систему Честный знак
         * Пример: "1720177239625"
         */
        fun createForChestnyZnakCheckMark(reqId: String, reqTimestamp: String): IndustryRequisite {
            return IndustryRequisite(
                // тег 1262 - признак ГИС МТ - 030
                foivId = "030",
                // тег 1263 - дата подписания ППР о запретительном режиме - 21 ноября 2023 года
                docDate = "2023-11-21T00:00:00.000Z",
                // тег 1264 - номер ППР о запретительном режиме - 1944
                docNumber = "1944",
                // тег 1265 - значения UUID запроса и Time запроса в систему Честный знак
                docData = "UUID=$reqId&Time=$reqTimestamp"
            )
        }
    }
}