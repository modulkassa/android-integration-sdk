package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PaymentErrorDetailsTest {

    @Test
    fun FromBundle_EmptyBundle_EmptySlip() {
        val bundle = Bundle()

        val result = PaymentErrorDetails.fromBundle(bundle)

        assertThat(result.slip, equalTo(listOf()))
        assertThat(result.message, equalTo(""))
    }

    @Test
    fun FromBundle_FilledBundle_FillsSlip() {
        val bundle = Bundle()
        bundle.putStringArrayList("slip", arrayListOf("some text"))
        bundle.putString("error_message", "some message")

        val result = PaymentErrorDetails.fromBundle(bundle)

        assertThat(result.slip, equalTo(listOf("some text")))
        assertThat(result.message, equalTo("some message"))
    }

    @Test
    fun ToBundle_ByDefault_SavesData() {
        val failedResult = PaymentErrorDetails(listOf("some text"), "message")

        val bundle = failedResult.toBundle()

        assertThat(bundle.getStringArrayList("slip"), equalTo(arrayListOf("some text")))
        assertThat(bundle.getString("error_message"), equalTo("message"))
    }

}