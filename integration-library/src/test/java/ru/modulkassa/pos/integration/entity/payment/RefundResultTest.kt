package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.math.BigDecimal

@RunWith(RobolectricTestRunner::class)
class RefundResultTest {

    @Test
    fun FromBundle_ByDefault_ReturnsResult() {
        val bundle = Bundle()

        val result = RefundResult.fromBundle(bundle)

        assertThat(result.slip, equalTo(listOf()))
        assertThat(result.transactionDetails, nullValue())
        assertThat(result.amount, nullValue())
        assertThat(result.certificate, nullValue())
    }

    @Test
    fun FromBundle_TransactionDetailsIsPresent_ReadsDataFromBundle() {
        val bundle = Bundle().apply {
            putString("transaction_details/payment_system_name", "name")
        }

        val result = RefundResult.fromBundle(bundle)

        assertThat(result.slip, equalTo(listOf()))
        assertThat(result.transactionDetails?.paymentSystemName, equalTo("name"))
    }

    @Test
    fun FromBundle_AmountAndCertificate_ReadsDataFromBundle() {
        val bundle = Bundle().apply {
            putString("amount", "12")
            putString("certificate", "{\"basketId\":\"123456\",\"certificateAmount\":1}")
        }

        val result = RefundResult.fromBundle(bundle)

        assertThat(result.amount, equalTo(BigDecimal.valueOf(12)))
        assertThat(result.certificate, equalTo(CertificateDetails("123456", BigDecimal.ONE)))
    }

    @Test
    fun ToBundle_ByDefault_SavesSlip() {
        val result = RefundResult(
            slip = listOf("some text")
        )

        val bundle = result.toBundle()

        assertThat(bundle.getStringArrayList("slip")?.toList(), equalTo(listOf("some text")))
        assertThat(bundle.keySet().any { it.startsWith("transaction_details") }, equalTo(false))
        assertThat(bundle.getString("payment_info"), nullValue())
        assertThat(bundle.getString("certificate"), nullValue())
    }

    @Test
    fun ToBundle_ByDefault_SavesRequestType() {
        val result = RefundResult(emptyList())

        val bundle = result.toBundle()

        assertThat(bundle.getString(RequestTypeSerialization.KEY), equalTo("REFUND"))
    }

    @Test
    fun ToBundle_TransactionDetailsIsPresent_SavesToBundle() {
        val result = RefundResult(
            slip = listOf("some text"),
            transactionDetails = TransactionDetails(terminalNumber = "number")
        )

        val bundle = result.toBundle()

        assertThat(bundle.getStringArrayList("slip")?.toList(), equalTo(listOf("some text")))
        assertThat(bundle.keySet().any { it.startsWith("transaction_details") }, equalTo(true))
    }

    @Test
    fun ToBundle_AmountAndCertificate_SavesFields() {
        val result = RefundResult(
            slip = emptyList(),
            amount = BigDecimal.TEN,
            certificate = CertificateDetails("123456", BigDecimal.ONE)
        )

        val bundle = result.toBundle()

        assertThat(bundle.getString("amount"), equalTo("10"))
        assertThat(bundle.getString("certificate"), equalTo("{\"basketId\":\"123456\",\"certificateAmount\":1}"))
    }

}