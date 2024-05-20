package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.math.BigDecimal

@RunWith(RobolectricTestRunner::class)
class CancelRequestTest {

    @Test
    fun FromBundle_NoPaymentInfo_PaymentInfoIsNull() {
        // given
        val bundle = Bundle()
        bundle.putString("payment_id", "")
        bundle.putString("amount", BigDecimal.ZERO.toPlainString())
        bundle.putString("description", "")
        // when
        val cancelRequest = CancelRequest.fromBundle(bundle)
        // then
        assertThat(cancelRequest.paymentInfo, `is`(nullValue()))
    }

    @Test
    fun FromBundle_PaymentInfoProvided_PaymentInfoInitialized() {
        // given
        val bundle = Bundle()
        bundle.putString("payment_id", "")
        bundle.putString("amount", BigDecimal.ZERO.toPlainString())
        bundle.putString("description", "")
        bundle.putString("payment_info", "")
        // when
        val cancelRequest = CancelRequest.fromBundle(bundle)
        // then
        assertThat(cancelRequest.paymentInfo, `is`(notNullValue()))
    }

    @Test
    fun ToBundle_ByDefault_SavesRequestType() {
        // given
        val request = CancelRequest(paymentId = "id", amount = BigDecimal.ZERO, description = "")

        // when
        val bundle = request.toBundle()
        // then
        assertThat(bundle.getString(RequestTypeSerialization.KEY), equalTo("CANCEL"))
    }

    @Test
    fun FromBundle_EmptyBundle_ReturnsDefaultValues() {
        val bundle = Bundle().apply {
            putString("amount", BigDecimal.ZERO.toPlainString())
        }

        val result = CancelRequest.fromBundle(bundle)

        assertThat(result.paymentId, equalTo(""))
        assertThat(result.amount, equalTo(BigDecimal.ZERO))
        assertThat(result.description, equalTo(""))
        assertThat(result.paymentInfo, nullValue())
        assertThat(result.certificate, nullValue())
        assertThat(result.merchantId, nullValue())
    }

    @Test
    fun FromBundle_FilledBundle_RestoresData() {
        val bundle = Bundle().apply {
            putString("payment_id", "check_id_123")
            putString("amount", "12")
            putString("description", "desc")
            putString("payment_info", "payment-info")
            putString("certificate", "{\"basketId\":\"123456\",\"certificateAmount\":1}")
            putString("merchant_id", "merchantId")
        }

        val result = CancelRequest.fromBundle(bundle)

        assertThat(result.paymentId, equalTo("check_id_123"))
        assertThat(result.amount, equalTo(BigDecimal.valueOf(12)))
        assertThat(result.description, equalTo("desc"))
        assertThat(result.paymentInfo, equalTo("payment-info"))
        assertThat(result.certificate, equalTo(CertificateDetails("123456", BigDecimal.ONE)))
        assertThat(result.merchantId, equalTo("merchantId"))
    }

    @Test
    fun ToBundle_Filled_SavesFields() {
        val result = CancelRequest(
            "payment-id", BigDecimal.TEN, "description", "payment-info",
            certificate = CertificateDetails("123456", BigDecimal.ONE), merchantId = "merchantId"
        )

        val bundle = result.toBundle()

        assertThat(bundle.getString("payment_id"), equalTo("payment-id"))
        assertThat(bundle.getString("amount"), equalTo("10"))
        assertThat(bundle.getString("description"), equalTo("description"))
        assertThat(bundle.getString("payment_info"), equalTo("payment-info"))
        assertThat(bundle.getString("certificate"), equalTo("{\"basketId\":\"123456\",\"certificateAmount\":1}"))
        assertThat(bundle.getString("merchant_id"), equalTo("merchantId"))
    }

    @Test
    fun ToBundle_NoCertificate_SavesNull() {
        val result = CancelRequest("payment-id", BigDecimal.TEN, "description", null, certificate = null)

        val bundle = result.toBundle()

        assertThat(bundle.getString("payment_info"), nullValue())
        assertThat(bundle.getString("certificate"), nullValue())
    }

}