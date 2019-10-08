package ru.modulkassa.pos.integration.core.rescue

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle

internal class RescueAnswerSenderViaIntent(
    private val context: Context?,
    private val intentSender: IntentSender?
) : RescueAnswerSender {

    override fun succeeded(data: Bundle?) {
        sendIntent {
            putExtras(data ?: Bundle.EMPTY)
            putExtra(RescueAnswerSender.RESULT_KEY, RescueAnswerSender.Result.SUCCESS.name)
        }
    }

    override fun failed(message: String?, extraData: Bundle?) {
        sendIntent {
            putExtra(RescueAnswerSender.RESULT_KEY, RescueAnswerSender.Result.FAILED.name)
            putExtra(RescueAnswerSender.MESSAGE_KEY, message ?: "")
        }
    }

    override fun cancelled() {
        sendIntent {
            putExtra(RescueAnswerSender.RESULT_KEY, RescueAnswerSender.Result.CANCELLED.name)
        }
    }

    private fun sendIntent(addExtras: Intent.() -> Unit) {
        val intent = Intent().apply {
            addExtras()
        }
        if (context != null) {
            intentSender?.sendIntent(context, 0, intent, null, null)
        }
    }

}