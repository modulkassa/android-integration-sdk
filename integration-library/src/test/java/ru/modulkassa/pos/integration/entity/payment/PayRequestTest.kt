package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
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

        assertThat(result.checkId, equalTo(""))
        assertThat(result.amount, equalTo(BigDecimal.ZERO))
        assertThat(result.description, equalTo(""))
        assertThat(result.merchantId, nullValue())
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

        assertThat(result.checkId, equalTo("check_id_123"))
        assertThat(result.amount, equalTo(BigDecimal.valueOf(12)))
        assertThat(result.description, equalTo("desc"))
        assertThat(result.merchantId, equalTo("123456"))
    }

    @Test
    fun ToBundle_Filled_SavesFields() {
        val result = PayRequest("checkId", BigDecimal.TEN, "description", "merchantId")

        val bundle = result.toBundle()

        assertThat(bundle.getString("check_id"), equalTo("checkId"))
        assertThat(bundle.getString("amount"), equalTo("10"))
        assertThat(bundle.getString("description"), equalTo("description"))
        assertThat(bundle.getString("merchant_id"), equalTo("merchantId"))
    }

    @Test
    fun ToBundle_NoMerchantId_SavesFields() {
        val result = PayRequest("checkId", BigDecimal.TEN, "description")

        val bundle = result.toBundle()

        assertThat(bundle.getString("check_id"), equalTo("checkId"))
        assertThat(bundle.getString("amount"), equalTo("10"))
        assertThat(bundle.getString("description"), equalTo("description"))
        assertThat(bundle.getString("merchant_id"), nullValue())
    }

    @Test
    fun ToBundle_ByDefault_SavesRequestType() {
        val request = PayRequest(checkId = "", amount = BigDecimal.ZERO, description = "")

        val bundle = request.toBundle()

        assertThat(bundle.getString(RequestTypeSerialization.KEY), equalTo("PAY"))
    }

}