package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PaymentErrorDetailsTest {

    @Test
    fun FromBundle_NullBundle_EmptySlip() {
        val bundle = null

        val result = PaymentErrorDetails.fromBundle(bundle)

        assertThat(result.slip, CoreMatchers.equalTo(listOf()))
    }

    @Test
    fun FromBundle_EmptyBundle_EmptySlip() {
        val bundle = Bundle()

        val result = PaymentErrorDetails.fromBundle(bundle)

        assertThat(result.slip, CoreMatchers.equalTo(listOf()))
    }

    @Test
    fun FromBundle_FilledBundle_FillsSlip() {
        val bundle = Bundle()
        bundle.putStringArrayList("slip", arrayListOf("some text"))

        val result = PaymentErrorDetails.fromBundle(bundle)

        assertThat(result.slip, CoreMatchers.equalTo(listOf("some text")))
    }

    @Test
    fun ToBundle_ByDefault_SavesData() {
        val failedResult = PaymentErrorDetails(listOf("some text"))

        val bundle = failedResult.toBundle()

        assertThat(bundle.getStringArrayList("slip"), CoreMatchers.equalTo(arrayListOf("some text")))
    }

}