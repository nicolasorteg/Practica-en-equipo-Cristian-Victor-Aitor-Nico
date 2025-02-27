package models

import java.time.LocalDate
import models.Persona
abstract class Jugadores(
    id:Long,
    nombre:String,
    apellidos:String,
    fechaNacimiento: LocalDate,
    fechaIncorporacion: LocalDate,
    salario:Double,
    pais:String,
    val especialidad:Especialidad,
    val posicion:Int,
    val dorsal:Int,
    val altura:Double,
    val peso:Double,
    val goles:Int,
    val partidosJugados:Int
):Persona(id, nombre, apellidos, fechaNacimiento, fechaIncorporacion, salario, pais)

enum class Especialidad {
DELANTERO,CENTROCAMPISTA,PORTERO,DEFENSA
}
