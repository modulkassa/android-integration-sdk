package ru.modulkassa.pos.integration.entity.check

import android.os.Bundle
import com.google.common.truth.Truth
import com.google.gson.Gson
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CheckTest {

    @Test
    fun FromBundle_FiscalDateAsString_ReturnsString() {
        val bundle = Bundle().also {
            it.putString(Check.KEY_SERIALIZED_CHECK, """{"fiscalInfo": {"date": "2020-06-01T13:03:26+07:00"} }""")
        }

        val check = Check.fromBundle(bundle)

        Truth.assertThat(check?.fiscalInfo?.date).isEqualTo("2020-06-01T13:03:26+07:00")
    }

    @Test
    fun DeserializeDate_BackwardCompatibility_ReturnsDateObject() {
        // В структуре FiscalInfo изменил тип для поля date с Date на String. Нужно обеспечить обратную совместимость
        // при получении данных через старую версию библиотеки (конвертацию строки ISO 8601 в Date).
        val dateString = """{"date": "2020-06-01T13:03:26+07:00"}"""

        val stubData = Gson().fromJson(dateString, StubData::class.java)

        val date = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply { set(2020, 5, 1, 6, 3, 26) }.time
        Truth.assertThat(stubData.date.toString()).isEqualTo(date.toString())
    }

    data class StubData (val date: Date)

}