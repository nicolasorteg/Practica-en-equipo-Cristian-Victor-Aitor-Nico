package utils

import services.Tipo

fun String.toTipo(): Tipo {
    return when(this){
        "CSV" -> Tipo.CSV
        "JSON"-> Tipo.JSON
        "XML" -> Tipo.XML
        else -> Tipo.BINARIO
    }
}

fun Tipo.toString():String{
    return when(this){
        Tipo.CSV -> "CSV"
        Tipo.JSON -> "JSON"
        Tipo.BINARIO -> "BINARIO"
        Tipo.XML -> "XML"
    }
}