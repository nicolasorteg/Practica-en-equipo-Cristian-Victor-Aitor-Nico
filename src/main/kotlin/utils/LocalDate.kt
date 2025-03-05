package utils

import com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDLoader.LOCALE
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

fun LocalDate.locale(localidad:String): String? {
    val Regex=Regex("^[a-z]_[A-Z]$")
    if (!localidad.matches(Regex)) {
        throw IllegalArgumentException("$localidad formato invalido")

    }
    val local=localidad.split("_")
    return this.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale(local[0],local[1])))
}
