package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.math.BigDecimal

@RunWith(RobolectricTestRunner::class)
class PayRequestTest {

    @Test
    fun FromBundle_EmptyBundle_ReturnsDefaultValues() {
        val bundle = Bundle()

        val result = PayRequest.fromBundle(bundle)

        assertThat(result.checkId, CoreMatchers.equalTo(""))
        assertThat(result.amount, CoreMatchers.equalTo(BigDecimal.ZERO))
        assertThat(result.description, CoreMatchers.equalTo(""))
        assertThat(result.merchantId, CoreMatchers.nullValue())
    }

    @Test
    fun FromBundle_FilledBundle_RestoresData() {
        val bundle = Bundle().apply {
            putString("check_id", "check_id_123")
            putString("amount", "12")
            putString("description", "desc")
            putString("merchant_id", "123456")
        }

        val result = PayRequest.fromBundle(bundle)

        assertThat(result.checkId, CoreMatchers.equalTo("check_id_123"))
        assertThat(result.amount, CoreMatchers.equalTo(BigDecimal.valueOf(12)))
        assertThat(result.description, CoreMatchers.equalTo("desc"))
        assertThat(result.merchantId, CoreMatchers.equalTo("123456"))
    }

    @Test
    fun ToBundle_Filled_SavesFields() {
        val result = PayRequest("checkId", BigDecimal.TEN, "description", "merchantId")

        val bundle = result.toBundle()

        assertThat(bundle.getString("check_id"), CoreMatchers.equalTo("checkId"))
        assertThat(bundle.getString("amount"), CoreMatchers.equalTo("10"))
        assertThat(bundle.getString("description"), CoreMatchers.equalTo("description"))
        assertThat(bundle.getString("merchant_id"), CoreMatchers.equalTo("merchantId"))
    }

    @Test
    fun ToBundle_NoMerchantId_SavesFields() {
        val result = PayRequest("checkId", BigDecimal.TEN, "description")

        val bundle = result.toBundle()

        assertThat(bundle.getString("check_id"), CoreMatchers.equalTo("checkId"))
        assertThat(bundle.getString("amount"), CoreMatchers.equalTo("10"))
        assertThat(bundle.getString("description"), CoreMatchers.equalTo("description"))
        assertThat(bundle.getString("merchant_id"), CoreMatchers.nullValue())
    }
}