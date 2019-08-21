package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.math.BigDecimal

@RunWith(RobolectricTestRunner::class)
class RefundRequestTest {

    @Test
    fun FromBundle_NoPaymentInfo_PaymentInfoIsNull() {
        // given
        val bundle = Bundle()
        bundle.putString("payment_id", "")
        bundle.putString("amount", BigDecimal.ZERO.toPlainString())
        bundle.putString("description", "")
        // when
        val refundRequest = RefundRequest.fromBundle(bundle)
        // then
        assertThat(refundRequest.paymentInfo, `is`(nullValue()))
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
        val refundRequest = RefundRequest.fromBundle(bundle)
        // then
        assertThat(refundRequest.paymentInfo, `is`(notNullValue()))
    }

    @Test
    fun ToBundle_ByDefault_SavesRequestType() {
        val request = RefundRequest("", BigDecimal.ZERO, "")

        val bundle = request.toBundle()

        assertThat(bundle.getString(RequestTypeSerialization.KEY), equalTo("REFUND"))
    }

}