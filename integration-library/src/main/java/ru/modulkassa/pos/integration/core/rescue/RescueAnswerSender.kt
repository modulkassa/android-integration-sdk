package ru.modulkassa.pos.integration.core.rescue

import android.os.Bundle

/**
 * Доставляет ответ до МодульКассы в случае обрыва связи, когда приложение выгружено из памяти.
 */
interface RescueAnswerSender {

    companion object {
        const val RESULT_KEY = "RescueAnswerSender.Result"
        const val MESSAGE_KEY = "RescueAnswerSender.Message"
    }

    /**
     * Признак результата
     */
    enum class Result {
        SUCCESS,
        FAILED,
        CANCELLED
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