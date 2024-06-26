package ru.modulkassa.pos.integration.core.manager

import android.content.Intent
import ru.modulkassa.pos.integration.core.ModulKassaApi.Companion.ACTION_SHIFT
import ru.modulkassa.pos.integration.core.ModulKassaApi.Companion.CLOSE_SHIFT_REQUEST
import ru.modulkassa.pos.integration.core.ModulKassaApi.Companion.KEY_SHIFT_REQUEST_TYPE
import ru.modulkassa.pos.integration.core.ModulKassaApi.Companion.OPEN_SHIFT_REQUEST
import ru.modulkassa.pos.integration.core.ModulKassaApi.Companion.X_REPORT_SHIFT_REQUEST
import ru.modulkassa.pos.integration.entity.ResultError
import ru.modulkassa.pos.integration.entity.check.Employee

internal class RealShiftManager(
    private val intentFactory: IntentFactory
) : ShiftManager {

    override fun createOpenShiftIntent(employee: Employee, packageName: String?): Intent {
        return createIntent(employee.name, OPEN_SHIFT_REQUEST, packageName)
    }

    override fun createCloseShiftIntent(employee: Employee, packageName: String?): Intent {
        return createIntent(employee.name, CLOSE_SHIFT_REQUEST, packageName)
    }

    override fun createXReportIntent(packageName: String?): Intent {
        return intentFactory.createIntent().apply {
            action = ACTION_SHIFT
            packageName?.let {
                `package` = it
            }
            putExtra(KEY_SHIFT_REQUEST_TYPE, X_REPORT_SHIFT_REQUEST)
        }
    }

    private fun createIntent(employeeName: String, requestType: String, packageName: String?): Intent {
        return intentFactory.createIntent().apply {
            action = ACTION_SHIFT
            packageName?.let {
                `package` = it
            }
            putExtra(KEY_SHIFT_REQUEST_TYPE, requestType)
            putExtras(Employee(name = employeeName).toBundle())
        }
    }

    override fun parseShiftActionResult(data: Intent): ResultError {
        return ResultError.fromBundle(data.extras)
    }
}