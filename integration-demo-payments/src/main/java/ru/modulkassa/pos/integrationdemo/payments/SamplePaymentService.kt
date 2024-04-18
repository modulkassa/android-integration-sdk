package ru.modulkassa.pos.integrationdemo.payments

import android.content.Intent
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.core.OperationHandler
import ru.modulkassa.pos.integration.core.PluginService
import ru.modulkassa.pos.integration.core.handler.CancelOperationHandler
import ru.modulkassa.pos.integration.core.handler.PayOperationHandler
import ru.modulkassa.pos.integration.core.handler.ReconciliationOperationHandler
import ru.modulkassa.pos.integration.core.handler.RefundOperationHandler
import ru.modulkassa.pos.integration.entity.payment.CancelRequest
import ru.modulkassa.pos.integration.entity.payment.CancelResult
import ru.modulkassa.pos.integration.entity.payment.PayRequest
import ru.modulkassa.pos.integration.entity.payment.ReconciliationResult
import ru.modulkassa.pos.integration.entity.payment.RefundRequest
import ru.modulkassa.pos.integration.entity.payment.RefundResult
import kotlin.concurrent.thread

class SamplePaymentService : PluginService() {

    override fun createHandlers(): List<OperationHandler> {
        return listOf(
            /**
             * Оплата
             */
            object : PayOperationHandler() {
                override fun handlePay(payRequest: PayRequest,
                                       callback: PluginServiceCallbackHolder) {
                    // запрос на оплату обрабатывается в отдельной активити
                    val payIntent = Intent(applicationContext, SamplePayActivity::class.java)
                    callback.putToIntent(payIntent)
                    payIntent.putExtra(SamplePayActivity.KEY_DATA, payRequest.toBundle())
                    payIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(payIntent)
                }
            },
            /**
             * Возврат
             */
            object : RefundOperationHandler() {
                override fun handleRefund(refundRequest: RefundRequest,
                                          callback: PluginServiceCallbackHolder) {
                    thread() {
                        Thread.sleep(2_000)
                        callback.get().succeeded(RefundResult(listOf()).toBundle())
                    }
                }
            },
            /**
             * Отмена транзакции (оплаты или возврата)
             */
            object : CancelOperationHandler() {
                override fun handleCancel(cancelRequest: CancelRequest,
                                          callback: PluginServiceCallbackHolder) {
                    thread() {
                        Thread.sleep(2_000)
                        callback.get().succeeded(CancelResult(listOf()).toBundle())
                    }
                }
            },
            /**
             * Сверка итогов
             */
            object : ReconciliationOperationHandler() {
                override fun handleReconciliation(callback: PluginServiceCallbackHolder) {
                    callback.get().succeeded(ReconciliationResult(listOf()).toBundle())
                }
            }

        )
    }

}