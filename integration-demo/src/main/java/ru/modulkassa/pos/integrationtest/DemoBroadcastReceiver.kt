package ru.modulkassa.pos.integrationtest

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import ru.modulkassa.pos.integration.entity.check.Check
import ru.modulkassa.pos.integration.event.CheckUpdatesBroadcastReceiver
import java.math.BigDecimal

class DemoBroadcastReceiver : CheckUpdatesBroadcastReceiver() {
    override fun onCheckCancelled(context: Context, checkId: String, linkedDocId: String?, sum: BigDecimal,
                                  check: Check) {
        Log.i("DemoBroadcastReceiver", "чек - $check")
        Toast.makeText(
            context,
            "Broadcast: Чек отменен: id - $checkId, linkedId - $linkedDocId, сумма - $sum",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onCheckChanged(context: Context, checkId: String, linkedDocId: String?, sum: BigDecimal,
                                check: Check) {
        Log.i("DemoBroadcastReceiver", "чек - $check")
        Toast.makeText(
            context,
            "Broadcast: Чек изменен: id - $checkId, linkedId - $linkedDocId, сумма - $sum",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onCheckClosed(context: Context, checkId: String, linkedDocId: String?,
                               sum: BigDecimal, check: Check) {
        Log.i("DemoBroadcastReceiver", "чек - $check")
        Toast.makeText(
            context,
            "Broadcast: Чек закрыт: id - $checkId, linkedId - $linkedDocId, сумма - $sum",
            Toast.LENGTH_LONG
        ).show()
    }
}