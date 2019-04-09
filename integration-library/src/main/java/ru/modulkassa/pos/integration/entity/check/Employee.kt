package ru.modulkassa.pos.integration.entity.check

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable

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

        fun fromBundle(bundle: Bundle): Employee? {
            return bundle.getString(KEY_EMPLOYEE_NAME)?.let {
                Employee(name = it)
            }
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(KEY_EMPLOYEE_NAME, name)
        }
    }
}