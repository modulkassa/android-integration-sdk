package ru.modulkassa.pos.integration.core.rescue

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle

class RescueAnswerSenderViaIntent(
    private val context: Context?,
    private val intentSender: IntentSender?
) : RescueAnswerSender {

    override fun succeeded(data: Bundle?) {
        sendIntent {
            putExtras(data ?: Bundle.EMPTY)
            putExtra(RescueAnswerSender.RESULT_KEY, RescueAnswerSender.RESULT_SUCCESS)
        }
    }

    override fun failed(message: String?, extraData: Bundle?) {
        sendIntent {
            putExtra(RescueAnswerSender.RESULT_KEY, RescueAnswerSender.RESULT_FAILED)
            putExtra(RescueAnswerSender.MESSAGE_KEY, message ?: "")
        }
    }

    override fun cancelled() {
        sendIntent {
            putExtra(RescueAnswerSender.RESULT_KEY, RescueAnswerSender.RESULT_CANCELLED)
        }
    }

    private fun sendIntent(addExtras: Intent.() -> Unit) {
        val intent = Intent().apply {
            addExtras()
        }
        intentSender?.sendIntent(context, 0, intent, null, null)
    }

}