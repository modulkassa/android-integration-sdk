package ru.modulkassa.pos.integration.entity.check

import ru.modulkassa.pos.integration.entity.check.InventType.INVENTORY
import ru.modulkassa.pos.integration.entity.check.PaymentMethod.FULL_PAYMENT
import java.math.BigDecimal

/**
 * Строка в чеке
 */
data class InventPosition(
    /**
     * Код товара
     */
    var inventCode: String?,
    /**
     * Наименование товара
     */
    var name: String,
    /**
     * Цена товара
     * Точность должна быть указана до 2х знаков [BigDecimal.setScale(2, BigDecimal.ROUND_DOWN)]
     */
    var price: BigDecimal,
    /**
     * Количество товара
     * Точность должна быть указана до 3х знаков [BigDecimal.setScale(3, BigDecimal.ROUND_DOWN)]
     */
    var quantity: BigDecimal,
    /**
     * Тег налоговой ставки
     */
    var vatTag: VatTag,
    /**
     * Единица измерения товара
     */
    var measure: Measure,
    /**
     * Штрихкод товара
     */
    var barcode: String? = null,
    /**
     * Тип товара (признак предмета расчета, тег 1212)
     */
    var inventType: InventType? = INVENTORY,
    /**
     * Признак способа расчета (тег 1214)
     */
    var paymentMethod: PaymentMethod? = FULL_PAYMENT,
    /**
     * Информация о платежном агенте
     */
    var agentInformation: InventPositionLevelAgentInformation? = null,
    /**
     * Сумма налога.
     * Необязательный параметр, сумма налога вычисленная внешней системой товарного учета. Передавать сумму налога
     * нужно вместе с суммой строки чека. Если передать только сумму строки чека, МодульКасса сама посчитает сумму
     * налога.
     * Точность должна быть указана до 2х знаков [BigDecimal.setScale(2, BigDecimal.ROUND_DOWN)]
     */
    var vatAmount: BigDecimal? = null,
    /**
     * Сумма строки в чеке.
     * Параметр имеет смысл передавать, если сумма строки в чеке отличается от произведения цены и количества.
     * Например, при вычислении суммы налога от суммы всей строки.
     * Точность должна быть указана до 2х знаков [BigDecimal.setScale(2, BigDecimal.ROUND_DOWN)]
     */
    var amount: BigDecimal? = null,
    /**
     * Код страны происхождения товара
     */
    var originCountryCode: String? = null,
    /**
     * Номер таможенной декларации
     */
    var customsDeclarationNumber: String? = null,
    /**
     * Сумма скидки без учета скидки для округления суммы чека
     * Значение заполняет приложение МодульКасса при возврате ответа.
     */
    var discountAmount: BigDecimal? = null,
    /**
     * Сумма скидки для округления суммы чека
     * Значение заполняет приложение МодульКасса при возврате ответа.
     */
    var roundDiscountAmount: BigDecimal? = null
)
