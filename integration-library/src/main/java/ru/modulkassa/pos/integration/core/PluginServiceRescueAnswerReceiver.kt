package ru.modulkassa.pos.integration.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ru.modulkassa.pos.integration.RescueAnswerReceiver

class PluginServiceRescueAnswerReceiver(
    private val context: Context
) : RescueAnswerReceiver {

    override fun succeeded(data: Bundle?) {
        sendIntent {
            putExtras(data ?: Bundle.EMPTY)
            putExtra(RescueAnswerReceiver.RESULT_KEY, RescueAnswerReceiver.RESULT_SUCCESS)
        }
    }

    override fun failed(message: String?, extraData: Bundle?) {
        sendIntent {
            putExtra(RescueAnswerReceiver.RESULT_KEY, RescueAnswerReceiver.RESULT_FAILED)
            putExtra(RescueAnswerReceiver.MESSAGE_KEY, message ?: "")
        }
    }

    override fun cancelled() {
        sendIntent {
            putExtra(RescueAnswerReceiver.RESULT_KEY, RescueAnswerReceiver.RESULT_CANCELLED)
        }
    }

    private fun sendIntent(addExtras: Intent.() -> Unit) {
        val intent = Intent("ru.modulkassa.pos.RESCUE_SEND_ANSWER").apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            addExtras()
        }
        context.startActivity(intent)
    }

}