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
        assertThat(result.transactionDetails, nullValue())
    }

    @Test
    fun FromBundle_WithAdditionalData_ReturnsResult() {
        val bundle = Bundle().apply {
            putString("transaction_details/authorization_code", "auth-code")
            putString("transaction_details/transaction_number", "transaction-number")
        }

        val result = PayResult.fromBundle(bundle)

        assertThat(result.transactionDetails?.authorizationCode, equalTo("auth-code"))
        assertThat(result.transactionDetails?.transactionNumber, equalTo("transaction-number"))
    }

    @Test
    fun ToBundle_ByDefault_SavesData() {
        val payResult = PayResult("cancel-id", listOf("some text"), "info")

        val bundle = payResult.toBundle()

        assertThat(bundle.getString("cancel_id"), equalTo("cancel-id"))
        assertThat(bundle.getString("payment_info"), equalTo("info"))
        assertThat(bundle.getString("payment_type"), equalTo("CARD"))
        assertThat(bundle.getStringArrayList("slip"), equalTo(arrayListOf("some text")))
        assertThat(bundle.keySet().any { it.startsWith("transaction_details") }, equalTo(false))
    }

    @Test
    fun ToBundle_ByDefault_SavesRequestType() {
        val payResult = PayResult("", emptyList(), "")

        val bundle = payResult.toBundle()

        assertThat(bundle.getString(RequestTypeSerialization.KEY), equalTo("PAY"))
    }

    @Test
    fun ToBundle_WithAdditionalData_SavesData() {
        val payResult = PayResult(
            paymentCancelId = "cancel-id",
            slip = listOf("some text"),
            transactionDetails = TransactionDetails(
                authorizationCode = "auth-code"
            )
        )

        val bundle = payResult.toBundle()

        assertThat(bundle.getString("transaction_details/authorization_code"), equalTo("auth-code"))
    }

}