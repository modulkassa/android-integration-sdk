package ru.modulkassa.pos.integrationdemo.offqr

import ru.modulkassa.pos.integration.entity.check.Check
import ru.modulkassa.pos.integration.entity.check.DocumentType.SALE
import ru.modulkassa.pos.integration.entity.check.InventPosition
import ru.modulkassa.pos.integration.entity.check.InventType.INVENTORY
import ru.modulkassa.pos.integration.entity.check.Measure.PCS
import ru.modulkassa.pos.integration.entity.check.MoneyPosition
import ru.modulkassa.pos.integration.entity.check.PaymentMethod.FULL_PAYMENT
import ru.modulkassa.pos.integration.entity.check.TaxationMode.COMMON
import ru.modulkassa.pos.integration.entity.check.VatTag.TAG_1102
import ru.modulkassa.pos.integration.entity.payment.PaymentType.CARD
import java.math.BigDecimal

val demoCheck = Check(
    id = "", // проинициализируем позже
    docType = SALE,
    employee = "Иванов Иван Иванович",
    email = "some@email.ru",
    inventPositions = listOf(
        InventPosition(
            name = "Товар",
            inventType = INVENTORY,
            price = BigDecimal.valueOf(10),
            vatTag = TAG_1102,
            quantity = BigDecimal.ONE,
            measure = PCS,
            inventCode = "2880000023757",
            paymentMethod = FULL_PAYMENT
        )
    ),
    moneyPositions = listOf(
        MoneyPosition(
            paymentType = CARD,
            sum = BigDecimal.valueOf(10),
            linkedId = "AD00112233445566778899AASSDDFFN"
        )
    ),
    taxMode = COMMON,
    orderId = "12345-order-id",
    responseURL = "some.response.url"
)