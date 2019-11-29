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
        assertThat(result.responseCode, equalTo("---"))
        assertThat(result.hostAnswerCode, equalTo("---"))
        assertThat(result.issuerAnswerCode, equalTo("---"))
    }

    @Test
    fun ToBundle_ByDefault_SavesData() {
        val failedResult = PaymentErrorDetails(listOf("some text"), "message")

        val bundle = failedResult.toBundle()

        assertThat(bundle.getStringArrayList("slip"), equalTo(arrayListOf("some text")))
        assertThat(bundle.getString("error_message"), equalTo("message"))
    }

    @Test
    fun ToBundle_ByDefault_SavesResponseCodes() {
        val failedResult = PaymentErrorDetails(slip = emptyList(), responseCode = "response code",
            hostAnswerCode = "host code", issuerAnswerCode = "issuer code")

        val bundle = failedResult.toBundle()

        assertThat(bundle.getString("response_code"), equalTo("response code"))
        assertThat(bundle.getString("host_answer_code"), equalTo("host code"))
        assertThat(bundle.getString("issuer_answer_code"), equalTo("issuer code"))
    }

}