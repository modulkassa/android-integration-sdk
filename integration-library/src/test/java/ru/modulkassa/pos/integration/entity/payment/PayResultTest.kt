package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import ru.modulkassa.pos.integration.entity.payment.PaymentType.CARD

@RunWith(RobolectricTestRunner::class)
class PayResultTest {

    @Test
    fun FromBundle_ByDefault_ReturnsResult() {
        val bundle = Bundle()

        val result = PayResult.fromBundle(bundle)

        assertThat(result.paymentCancelId, equalTo(""))
        assertThat(result.slip, equalTo(listOf()))
        assertThat(result.paymentType, equalTo(CARD))
        assertThat(result.paymentInfo, nullValue())
    }

    @Test
    fun ToBundle_ByDefault_SavesData() {
        val payResult = PayResult("cancel-id", listOf("some text"), "info")

        val bundle = payResult.toBundle()

        assertThat(bundle.getString("cancel_id"), equalTo("cancel-id"))
        assertThat(bundle.getString("payment_info"), equalTo("info"))
        assertThat(bundle.getString("payment_type"), equalTo("CARD"))
        assertThat(bundle.getStringArrayList("slip"), equalTo(arrayListOf("some text")))
    }

    @Test
    fun ToBundle_WithAdditionalData_SavesData() {
        val payResult = PayResult(
            paymentCancelId = "cancel-id",
            slip = listOf("some text"),
            authorizationCode = "auth-code",
            transactionNumber = "transaction-number",
            maskedCardNumber = "card-number",
            cardExpiryDate = "expiry-date",
            operationDateTime = "datetime",
            terminalNumber = "terminal-number",
            paymentSystemName = "payment-system-name"
        )

        val bundle = payResult.toBundle()

        assertThat(bundle.getString("authorization_code"), equalTo("auth-code"))
        assertThat(bundle.getString("transaction_number"), equalTo("transaction-number"))
        assertThat(bundle.getString("masked_card_number"), equalTo("card-number"))
        assertThat(bundle.getString("card_expiry_date"), equalTo("expiry-date"))
        assertThat(bundle.getString("operation_datetime"), equalTo("datetime"))
        assertThat(bundle.getString("terminal_number"), equalTo("terminal-number"))
        assertThat(bundle.getString("payment_system_name"), equalTo("payment-system-name"))
    }

}