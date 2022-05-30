package ru.modulkassa.pos.integration.intent

import android.content.Intent
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import ru.modulkassa.pos.integration.entity.check.Check

/**
 * Intent для печати(фискализации) чека в рамках приложения МодульКасса
 */
@Deprecated("Используйте ```CheckManager.createPrintCheckIntent()```")
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
            return try {
                val gson = Gson()
                gson.fromJson(serializedCheck, Check::class.java)
            } catch (e: IllegalStateException) {
                throw InvalidCheckBodyException(serializedCheck, e)
            } catch (e: JsonSyntaxException) {
                throw InvalidCheckBodyException(serializedCheck, e)
            } catch (e: NullPointerException) {
                throw InvalidCheckBodyException(serializedCheck, e)
            }
        }

        fun employeeNameFromIntent(intent: Intent): String {
            val employeeName = intent.getStringExtra(KEY_EMPLOYEE_NAME)
            return employeeName ?: throw EmployeeNameNotFoundException()
        }

        fun pinFromIntent(intent: Intent): String {
            val pin = intent.getStringExtra(KEY_PIN)
            return pin ?: throw PinNotFoundException()
        }
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