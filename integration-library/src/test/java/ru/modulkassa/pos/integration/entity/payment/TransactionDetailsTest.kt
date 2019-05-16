package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TransactionDetailsTest {

    @Test
    fun FromBundle_EmptyBundle_ReturnsNull() {
        val transactionDetails = TransactionDetails.fromBundle(Bundle.EMPTY)

        assertThat(transactionDetails, nullValue())
    }

    @Test
    fun FromBundle_ByDefault_ReturnsResult() {
        val bundle = Bundle().apply {
            putString("transaction_details/authorization_code", "auth-code")
            putString("transaction_details/transaction_number", "transaction-number")
            putString("transaction_details/masked_card_number", "card-number")
            putString("transaction_details/card_expiry_date", "expiry-date")
            putString("transaction_details/operation_datetime", "datetime")
            putString("transaction_details/terminal_number", "terminal-number")
            putString("transaction_details/payment_system_name", "payment-system-name")
        }

        val result = TransactionDetails.fromBundle(bundle)

        assertThat(result?.authorizationCode, equalTo("auth-code"))
        assertThat(result?.transactionNumber, equalTo("transaction-number"))
        assertThat(result?.maskedCardNumber, equalTo("card-number"))
        assertThat(result?.cardExpiryDate, equalTo("expiry-date"))
        assertThat(result?.operationDateTime, equalTo("datetime"))
        assertThat(result?.terminalNumber, equalTo("terminal-number"))
        assertThat(result?.paymentSystemName, equalTo("payment-system-name"))
    }

    @Test
    fun ToBundle_WithAdditionalData_SavesData() {
        val payResult = TransactionDetails(
            authorizationCode = "auth-code",
            transactionNumber = "transaction-number",
            maskedCardNumber = "card-number",
            cardExpiryDate = "expiry-date",
            operationDateTime = "datetime",
            terminalNumber = "terminal-number",
            paymentSystemName = "payment-system-name"
        )

        val bundle = payResult.toBundle()

        assertThat(bundle.getString("transaction_details/authorization_code"), equalTo("auth-code"))
        assertThat(bundle.getString("transaction_details/transaction_number"), equalTo("transaction-number"))
        assertThat(bundle.getString("transaction_details/masked_card_number"), equalTo("card-number"))
        assertThat(bundle.getString("transaction_details/card_expiry_date"), equalTo("expiry-date"))
        assertThat(bundle.getString("transaction_details/operation_datetime"), equalTo("datetime"))
        assertThat(bundle.getString("transaction_details/terminal_number"), equalTo("terminal-number"))
        assertThat(bundle.getString("transaction_details/payment_system_name"), equalTo("payment-system-name"))
    }

}