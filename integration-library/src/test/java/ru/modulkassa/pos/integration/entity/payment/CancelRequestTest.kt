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

}