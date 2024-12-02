package ru.modulkassa.pos.integration.core.manager

import android.content.Intent
import ru.modulkassa.pos.integration.core.ModulKassaApi.Companion.ACTION_CHECK_REGISTRATION
import ru.modulkassa.pos.integration.core.ModulKassaApi.Companion.ACTION_MONEY_CHECK_REGISTRATION
import ru.modulkassa.pos.integration.core.ModulKassaApi.Companion.KEY_CASH_DOCUMENT_TYPE
import ru.modulkassa.pos.integration.entity.ResultError
import ru.modulkassa.pos.integration.entity.check.Check
import ru.modulkassa.pos.integration.entity.kkt.MoneyCheck

internal class RealCheckManager(
    private val intentFactory: IntentFactory
) : CheckManager {

    override fun createPrintCheckIntent(check: Check): Intent {
        return intentFactory.createIntent().apply {
            action = ACTION_CHECK_REGISTRATION
            putExtra(KEY_CASH_DOCUMENT_TYPE, check.docType.name)
            putExtras(check.toBundle())
        }
    }

    override fun parsePrintCheckSuccess(data: Intent): Check? {
        return data.extras?.let { Check.fromBundle(it) }
    }

    override fun parsePrintCheckError(data: Intent): ResultError {
        return ResultError.fromBundle(data.extras)
    }

    override fun createMoneyCheckIntent(check: MoneyCheck): Intent {
        return intentFactory.createIntent().apply {
            action = ACTION_MONEY_CHECK_REGISTRATION
            putExtras(check.toBundle())
        }
    }

    override fun parseMoneyCheckError(data: Intent): ResultError {
        return ResultError.fromBundle(data.extras)
    }

}