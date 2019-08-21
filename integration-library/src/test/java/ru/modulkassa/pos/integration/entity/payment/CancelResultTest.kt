package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CancelResultTest {

    @Test
    fun FromBundle_ByDefault_ReturnsResult() {
        val bundle = Bundle()

        val result = CancelResult.fromBundle(bundle)

        assertThat(result.slip, equalTo(listOf()))
        assertThat(result.transactionDetails, nullValue())
    }

    @Test
    fun FromBundle_TransactionDetailsIsPresent_RestoresData() {
        val bundle = Bundle().apply {
            putString("transaction_details/payment_system_name", "name")
        }

        val result = CancelResult.fromBundle(bundle)

        assertThat(result.slip, equalTo(listOf()))
        assertThat(result.transactionDetails?.paymentSystemName, equalTo("name"))
    }

    @Test
    fun ToBundle_ByDefault_SavesSlip() {
        val result = CancelResult(listOf("some text"))

        val bundle = result.toBundle()

        assertThat(bundle.getStringArrayList("slip")?.toList(), equalTo(listOf("some text")))
        assertThat(bundle.keySet().any { it.startsWith("transaction_details") }, equalTo(false))
    }

    @Test
    fun ToBundle_ByDefault_SavesRequestType() {
        val result = CancelResult(emptyList())

        val bundle = result.toBundle()

        assertThat(bundle.getString(RequestTypeSerialization.KEY), equalTo("CANCEL"))
    }

    @Test
    fun ToBundle_TransactionDetailsIsPresent_SavesSlip() {
        val result = CancelResult(
            slip = listOf("some text"),
            transactionDetails = TransactionDetails(terminalNumber = "number")
        )

        val bundle = result.toBundle()

        assertThat(bundle.getStringArrayList("slip")?.toList(), equalTo(listOf("some text")))
        assertThat(bundle.keySet().any { it.startsWith("transaction_details") }, equalTo(true))
    }

}