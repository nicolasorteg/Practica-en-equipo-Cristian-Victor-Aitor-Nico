package utils

import models.Especialidad
import models.Posicion
import services.Tipo
import java.util.*

fun String.toTipo(): Tipo {
    return when(this.uppercase(Locale.getDefault())){
        "CSV" -> Tipo.CSV
        "JSON"-> Tipo.JSON
        "XML" -> Tipo.XML
        else -> Tipo.BINARIO
    }
}

fun Tipo.toStringFile():String{
    return when(this){
        Tipo.CSV -> "CSV"
        Tipo.JSON -> "JSON"
        Tipo.BINARIO -> "BIN"
        Tipo.XML -> "XML"
    }
}
fun String.toEspecialidad(): Especialidad? {
    return if (this.uppercase(Locale.getDefault()) != "ENTRENADOR_PORTEROS" &&
        this.uppercase(Locale.getDefault()) != "ENTRENADOR_ASISTENTE" &&
        this.uppercase(Locale.getDefault()) != "ENTRENADOR_PRINCIPAL")
        null else Especialidad.valueOf(this.uppercase(Locale.getDefault()))
}
fun String.toPosicion(): Posicion? {
    return if (this.uppercase(Locale.getDefault()) != "DELANTERO" &&
        this.uppercase(Locale.getDefault()) != "CENTROCAMPISTA" && this.uppercase(Locale.getDefault()) != "PORTERO" &&
        this.uppercase(Locale.getDefault()) != "DEFENSA") null else Posicion.valueOf(this.uppercase(Locale.getDefault()))

}