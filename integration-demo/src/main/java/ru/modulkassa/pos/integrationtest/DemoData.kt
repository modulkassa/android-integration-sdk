package ru.modulkassa.pos.integrationtest

import ru.modulkassa.pos.integration.entity.check.Check
import ru.modulkassa.pos.integration.entity.check.ClientInformation
import ru.modulkassa.pos.integration.entity.check.DocumentType.SALE
import ru.modulkassa.pos.integration.entity.check.InventPosition
import ru.modulkassa.pos.integration.entity.check.InventType.INVENTORY
import ru.modulkassa.pos.integration.entity.check.Measure.PCS
import ru.modulkassa.pos.integration.entity.check.MoneyPosition
import ru.modulkassa.pos.integration.entity.check.ReportLine
import ru.modulkassa.pos.integration.entity.check.ReportLineType.QR
import ru.modulkassa.pos.integration.entity.check.ReportLineType.TEXT
import ru.modulkassa.pos.integration.entity.check.TaxationMode.COMMON
import ru.modulkassa.pos.integration.entity.check.VatTag.TAG_1103
import ru.modulkassa.pos.integration.entity.payment.PaymentType.CASH
import java.math.BigDecimal

val demoCheck = Check(
    id = "", // проинициализируем позже
    docType = SALE,
    employee = "Иванов Иван Иванович",
    printReceipt = true,
    email = "some@email.ru",
    inventPositions = listOf(
        InventPosition(
            name = "Материнская плата AS—Rock H32M R3.0, Socket1150, iH81, 2DDR3, PCI-Ex16, 2SATA2, 2SATA3",
            price = BigDecimal("200"),
            barcode = "2880000023757",
            vatTag = TAG_1103,
            quantity = BigDecimal.ONE,
            measure = PCS,
            inventCode = "2880000023757",
            inventType = INVENTORY,
            originCountryCode = "22",
            customsDeclarationNumber = "declaration 11"
        ),
        InventPosition(
            name = "Жесткий диск Seagate",
            price = BigDecimal("100"),
            barcode = "2880000023757",
            vatTag = TAG_1103,
            quantity = BigDecimal.ONE,
            measure = PCS,
            inventCode = "2880000023757"
        )

    ),
    moneyPositions = listOf(MoneyPosition(
        paymentType = CASH,
        sum = BigDecimal("1000")
    )),
    taxMode = COMMON,
    textToPrint = "Текст для дополнительной\nпечати на чеке",
    clientInformation = ClientInformation("ООО Фирма", null, "4959166101")
)

val linesForPrinting = ArrayList<ReportLine>().apply {
    add(ReportLine("        ООО 'Магазин-2014'        ", TEXT))
    add(ReportLine("ИНН: 4959166101     КПП: 495901001", TEXT))
    add(ReportLine("КАССА: 11022            СМЕНА: 693", TEXT))
    add(ReportLine("ЧЕК: 3027   ДАТА: 13.12.2012 11:12", TEXT))
    add(ReportLine("                                  ", TEXT))
    add(ReportLine("http://check.egais.ru?id=88a7a3ed-39ae-45de-a3cc-644639f36f4e&dt=0910141104&" +
        "cn=030000255555", QR))
    add(ReportLine("                                  ", TEXT))
    addAll(("http://check.egais.ru?id=88a7a3ed-39ae-45de-a3cc-644639f36f4e&dt=0910141104&" +
        "cn=030000255555").chunked(34).map { line -> ReportLine(line, TEXT) })
    addAll(("04 40 EA 2B C7 08 75 5D F0 43 C1 04 5C 06 96 71 69 DD BF 30 D9 2D 6B 7D F0 FE 81" +
        " 43 F9 C4 51 21 E3 42 C9 67 63 4F 24 D5 42 B1 8B 1D 3D F8 6F 91 21 00 6D 8B DE 56 91" +
        " CA BB ED 0D 36 11 96 B4 33").chunked(34).map { line -> ReportLine(line, TEXT) })
}