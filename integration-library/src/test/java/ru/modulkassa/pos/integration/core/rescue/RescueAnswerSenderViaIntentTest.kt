package ru.modulkassa.pos.integration.core.rescue

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RescueAnswerSenderViaIntentTest {

    @Test
    fun Succeeded_ByDefault_SendsIntent() {
        val intentSender = mock<IntentSender>()
        val sender = createSender(intentSender = intentSender)

        sender.succeeded(Bundle().apply { putString("data_key", "some text") })

        argumentCaptor<Intent>().apply {
            verify(intentSender).sendIntent(any(), any(), capture(), anyOrNull(), anyOrNull())
            assertThat(firstValue.getStringExtra("RescueAnswerSender.Result"), equalTo("SUCCESS"))
            assertThat(firstValue.getStringExtra("data_key"), equalTo("some text"))
        }
    }

    @Test
    fun Failed_ByDefault_SendsIntent() {
        val intentSender = mock<IntentSender>()
        val sender = createSender(intentSender = intentSender)

        sender.failed("some error", Bundle.EMPTY)

        argumentCaptor<Intent>().apply {
            verify(intentSender).sendIntent(any(), any(), capture(), anyOrNull(), anyOrNull())
            assertThat(firstValue.getStringExtra("RescueAnswerSender.Result"), equalTo("FAILED"))
            assertThat(firstValue.getStringExtra("RescueAnswerSender.Message"), equalTo("some error"))
        }
    }

    @Test
    fun Cancelled_ByDefault_SendsIntent() {
        val intentSender = mock<IntentSender>()
        val sender = createSender(intentSender = intentSender)

        sender.cancelled()

        argumentCaptor<Intent>().apply {
            verify(intentSender).sendIntent(any(), any(), capture(), anyOrNull(), anyOrNull())
            assertThat(firstValue.getStringExtra("RescueAnswerSender.Result"), equalTo("CANCELLED"))
        }
    }

    @Test
    fun Cancelled_ContextIsAbsent_DoesNothing() {
        val intentSender = mock<IntentSender>()
        val sender = createSender(context = null, intentSender = intentSender)

        sender.cancelled()

        verify(intentSender, never()).sendIntent(anyOrNull(), any(), any(), anyOrNull(), anyOrNull())
    }

    private fun createSender(
        context: Context? = mock(),
        intentSender: IntentSender = mock()
    ): RescueAnswerSenderViaIntent {
        return RescueAnswerSenderViaIntent(context, intentSender)
    }

}
