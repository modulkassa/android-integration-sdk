package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import com.google.gson.reflect.TypeToken
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.GsonFactory
import ru.modulkassa.pos.integration.entity.payment.RefundResult.Companion
import java.math.BigDecimal

/**
 * Данные результата успешной отмены
 */
data class CancelResult(
    /**
     * Информация от платежной системы, которую необходимо распечатать на чеке
     * Если слипов два, для них используется строка-разделитель Slip.DELIMITER_VALUE
     */
    val slip: List<String>,
    /**
     * Данные транзакции
     */
    val transactionDetails: TransactionDetails? = null,
    /**
     * Полная сумма по транзакции
     */
    val amount: BigDecimal? = null,
    /**
     * Данные для платежа с использованием электронного сертификата
     */
    val certificate: CertificateDetails? = null
) : Bundable {

    companion object {
        private const val KEY_SLIP = "slip"
        private const val KEY_AMOUNT = "amount"
        private const val KEY_CERT = "certificate"

        private val gson = GsonFactory.provide()

        fun fromBundle(bundle: Bundle): CancelResult {
            return CancelResult(
                slip = bundle.getStringArrayList(KEY_SLIP) ?: arrayListOf(),
                transactionDetails = TransactionDetails.fromBundle(bundle),
                amount = bundle.getString(KEY_AMOUNT)?.let { BigDecimal(it) },
                certificate = bundle.getString(KEY_CERT)?.let {
                    gson.fromJson(it, object : TypeToken<CertificateDetails>() {}.type)
                }
            )
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putStringArrayList(KEY_SLIP, ArrayList(slip))
            putAll(transactionDetails?.toBundle() ?: Bundle.EMPTY)
            putString(RequestTypeSerialization.KEY, RequestType.CANCEL.name)
            amount?.let { putString(KEY_AMOUNT, it.toPlainString()) }
            certificate?.let { putString(KEY_CERT, gson.toJson(certificate)) }
        }
    }
}