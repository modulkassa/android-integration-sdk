package ru.modulkassa.pos.integration.intent

import android.content.Intent
import com.google.gson.Gson
import ru.modulkassa.pos.integration.entity.check.Check

/**
 * Intent для печати(фискализации) чека в рамках приложения МодульКасса
 */
class PrintCheckIntent(
    /**
     * Чек, который необходимо распечатать/фискализировать
     */
    check: Check,
    /**
     * Имя кассира (должно совпадать с именем кассира в приложении МодульКасса)
     */
    employeeName: String,
    /**
     * PIN кассира
     */
    pin: String
) : Intent() {

    companion object {
        val ACTION = "ru.modulkassa.pos.PRINT_CHECK"
        private val KEY_SERIALIZED_CHECK = "serialized_check"
        private val KEY_EMPLOYEE_NAME = "employee_name"
        private val KEY_PIN = "pin"

        fun checkFromIntent(intent: Intent): Check {
            val serializedCheck = intent.getStringExtra(KEY_SERIALIZED_CHECK)
            val gson = Gson()
            return gson.fromJson(serializedCheck, Check::class.java)
        }

        fun employeeNameFromIntent(intent: Intent) = intent.getStringExtra(KEY_EMPLOYEE_NAME)

        fun pinFromIntent(intent: Intent) = intent.getStringExtra(KEY_PIN)
    }

    init {
        action = ACTION
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val gson = Gson()
        putExtra(KEY_SERIALIZED_CHECK, gson.toJson(check))
        putExtra(KEY_EMPLOYEE_NAME, employeeName)
        putExtra(KEY_PIN, pin)
    }
}