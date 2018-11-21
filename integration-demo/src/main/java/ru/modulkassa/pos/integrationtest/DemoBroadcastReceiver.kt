package ru.modulkassa.pos.integrationtest

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import ru.modulkassa.pos.integration.entity.check.Check
import ru.modulkassa.pos.integration.event.CheckUpdatesBroadcastReceiver
import java.math.BigDecimal

class DemoBroadcastReceiver : CheckUpdatesBroadcastReceiver() {
    override fun onCheckClosed(context: Context, checkId: String, linkedDocId: String?,
                               sum: BigDecimal, check: Check) {
        Log.i("DemoBroadcastReceiver", "чек - $check")
        Toast.makeText(
            context,
            "Чек закрыт: id - $checkId, linkedId - $linkedDocId, сумма - $sum, чек - $check}",
            Toast.LENGTH_LONG
        ).show()
    }
}