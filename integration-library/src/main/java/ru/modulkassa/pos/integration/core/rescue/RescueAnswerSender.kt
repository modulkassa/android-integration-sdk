package ru.modulkassa.pos.integration.core.rescue

import android.os.Bundle

/**
 * Доставляет ответ до МодульКассы в случае обрыва связи, когда приложение выгружено из памяти.
 */
interface RescueAnswerSender {

    companion object {
        const val RESULT_KEY = "RescueAnswerSender.Result"
        const val MESSAGE_KEY = "RescueAnswerSender.Message"

        const val RESULT_SUCCESS = 1
        const val RESULT_FAILED = 2
        const val RESULT_CANCELLED = 3
    }

    /**
     * Успех
     */
    fun succeeded(data: Bundle?)

    /**
     * Ошибка
     */
    fun failed(message: String?, extraData: Bundle?)

    /**
     * Отменено пользователем
     */
    fun cancelled()

}