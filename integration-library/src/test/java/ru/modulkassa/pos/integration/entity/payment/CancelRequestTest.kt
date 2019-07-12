package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
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
    fun FromBundle_WithMerchantId_RestoresData() {
        val bundle = Bundle().apply {
            putString("payment_id", "")
            putString("amount", BigDecimal.ZERO.toPlainString())
            putString("description", "")
            putString("merchant_id", "123456")
        }

        val result = CancelRequest.fromBundle(bundle)

        Assert.assertThat(result.merchantId, CoreMatchers.equalTo("123456"))
    }


    @Test
    fun FromBundle_NoMerchantId_NoValue() {
        val bundle = Bundle().apply {
            putString("payment_id", "")
            putString("amount", BigDecimal.ZERO.toPlainString())
            putString("description", "")
        }

        val result = CancelRequest.fromBundle(bundle)

        Assert.assertThat(result.merchantId, CoreMatchers.nullValue())
    }

    @Test
    fun ToBundle_WithMerchantId_SavesFields() {
        val result = CancelRequest("checkId", BigDecimal.TEN, "description", merchantId = "merchantId")

        val bundle = result.toBundle()

        Assert.assertThat(bundle.getString("merchant_id"), CoreMatchers.equalTo("merchantId"))
    }

    @Test
    fun ToBundle_NoMerchantId_NoValue() {
        val result = CancelRequest("checkId", BigDecimal.TEN, "description")

        val bundle = result.toBundle()

        Assert.assertThat(bundle.getString("merchant_id"), nullValue())
    }
}