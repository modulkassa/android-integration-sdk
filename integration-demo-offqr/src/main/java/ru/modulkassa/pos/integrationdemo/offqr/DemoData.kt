package ru.modulkassa.pos.integrationdemo.offqr

import ru.modulkassa.pos.integration.entity.check.Check
import ru.modulkassa.pos.integration.entity.check.ClientInformation
import ru.modulkassa.pos.integration.entity.check.DocumentType.SALE
import ru.modulkassa.pos.integration.entity.check.IndustryRequisite
import ru.modulkassa.pos.integration.entity.check.InventPosition
import ru.modulkassa.pos.integration.entity.check.InventType.INVENTORY
import ru.modulkassa.pos.integration.entity.check.Measure.PCS
import ru.modulkassa.pos.integration.entity.check.MoneyPosition
import ru.modulkassa.pos.integration.entity.check.TaxationMode.COMMON
import ru.modulkassa.pos.integration.entity.check.VatTag.TAG_1103
import ru.modulkassa.pos.integration.entity.payment.PaymentType.CARD
import java.math.BigDecimal

val demoCheck = Check(
    id = "", // проинициализируем позже
    docType = SALE,
    employee = "Иванов Иван Иванович",
    printReceipt = true,
    email = "some@email.ru",
    inventPositions = listOf(
        InventPosition(
            name = "Товар",
            price = BigDecimal("200"),
            barcode = "2880000023757",
            vatTag = TAG_1103,
            quantity = BigDecimal.ONE,
            measure = PCS,
            inventCode = "2880000023757",
            inventType = INVENTORY
        )
    ),
    moneyPositions = listOf(
        MoneyPosition(
            paymentType = CARD,
            sum = BigDecimal("200")
        )
    ),
    taxMode = COMMON,
    orderId = "12345-order-id",
    responseURL = "some.response.url"
)