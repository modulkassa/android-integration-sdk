package ru.modulkassa.pos.integration.entity.check

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.InvalidEntityStructureException

/**
 * Сотрудник
 */
data class Employee(
    /**
     * ФИО
     */
    val name: String
) : Bundable {

    companion object {
        const val KEY_EMPLOYEE_NAME = "integration.entity.employee.employee_name"
        private val gson = Gson()

        fun fromBundle(bundle: Bundle): Employee? {
            return bundle.getString(KEY_EMPLOYEE_NAME)?.let {
                Employee(name = it)
            }
        }

        fun fromJson(json: String): Employee {
            try {
                val employee = gson.fromJson(json, Employee::class.java)
                return employee ?: throw IllegalStateException("Данные о сотруднике не могут быть пустыми")
            } catch (error: JsonSyntaxException) {
                throw InvalidEntityStructureException("Некорректная структура данных о сотруднике", error)
            }
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(KEY_EMPLOYEE_NAME, name)
        }
    }

    fun toJson(): String = gson.toJson(this)
}