package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import ru.modulkassa.pos.integration.entity.payment.PaymentType.CARD
import java.math.BigDecimal

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
        assertThat(result.amount, nullValue())
        assertThat(result.certificate, nullValue())
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
    fun FromBundle_WithCertificate_ReturnsResult() {
        val bundle = Bundle().apply {
            putString("certificate", "{\"basketId\":\"basketId\",\"certificateAmount\":1}")
        }

        val result = PayResult.fromBundle(bundle)

        assertThat(result.certificate?.basketId, equalTo("basketId"))
        assertThat(result.certificate?.certificateAmount, equalTo(BigDecimal.ONE))
    }

    @Test
    fun ToBundle_ByDefault_SavesData() {
        val payResult = PayResult("cancel-id", listOf("some text"), "info", amount = BigDecimal.ONE,
        certificate = CertificateDetails("basketId", BigDecimal.ONE))

        val bundle = payResult.toBundle()

        assertThat(bundle.getString("cancel_id"), equalTo("cancel-id"))
        assertThat(bundle.getString("payment_info"), equalTo("info"))
        assertThat(bundle.getString("payment_type"), equalTo("CARD"))
        assertThat(bundle.getStringArrayList("slip"), equalTo(arrayListOf("some text")))
        assertThat(bundle.keySet().any { it.startsWith("transaction_details") }, equalTo(false))
        assertThat(bundle.getString("amount"), equalTo("1"))
        assertThat(bundle.getString("certificate"), equalTo("{\"basketId\":\"basketId\",\"certificateAmount\":1}"))
    }

    @Test
    fun ToBundle_NullAmount_NoKey() {
        val payResult = PayResult("cancel-id", listOf("some text"), amount = null)

        val bundle = payResult.toBundle()

        assertThat(bundle.getString("amount"), nullValue())
    }

    @Test
    fun ToBundle_NullCertificate_NoKey() {
        val payResult = PayResult("cancel-id", listOf("some text"), certificate = null)

        val bundle = payResult.toBundle()

        assertThat(bundle.getString("certificate"), nullValue())
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