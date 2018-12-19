package ru.modulkassa.pos.integration.entity.check

/**
 * Документ из другого приложения, который необходимо фискализировать
 */
data class Check(
    /**
     * Идентификатор документа
     */
    var id: String,
    /**
     * Тип документа
     */
    var docType: DocumentType,
    /**
     * Печатать чек или нет
     */
    var printReceipt: Boolean = false,
    /**
     * Адрес электронной почты или телефона покупателя
     *
     * Допустимые форматы:
     * +{цифры}
     * {символы}@{символы}
     * Допустимые символы:
     * Цифры от 0 до 9.
     * Буквы латинского алфавита в любом регистре.
     * Общие разделители и специальные символы.
     */
    var email: String?,
    /**
     * Позиции документа/чека
     */
    var inventPositions: List<InventPosition>,
    /**
     * Оплаты
     */
    var moneyPositions: List<MoneyPosition>,
    /**
     * ФИО кассира. Согласно ФЗ-54 необходимо указывать ФИО работника и его должность
     */
    var employee: String = "",
    /**
     * Тип СНО
     */
    var taxMode: TaxationMode,
    /**
     * Идентификатор чека в рамках МодульКассы. Устанавливать не обязательно. Будет заполняться,
     * если чек приходит от приложения МодульКассы
     */
    var modulKassaId: String? = null,
    /**
     * Текст, который должен быть распечатан на чеке
     */
    var textToPrint: String? = null,
    /**
     * Информация о зарегистрированном чеке из ФН
     */
    var fiscalInfo: FiscalInfo? = null
)