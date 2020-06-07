package app.ido.umi.hozonactivity

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CalendarSample {
    fun main(args: Array<String>) {
        val cal = Calendar.getInstance()
        cal.time = Date()
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        println("current: ${df.format(cal.time)}")

        cal.add(Calendar.MONTH, 2)
        cal.add(Calendar.DATE, -3)
        println("after: ${df.format(cal.time)}")
    }
}