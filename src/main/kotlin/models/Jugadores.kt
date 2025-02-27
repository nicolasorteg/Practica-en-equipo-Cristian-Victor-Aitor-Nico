package models

import java.time.LocalDate
<<<<<<< HEAD
import models.Persona
 class Jugadores(
=======

abstract class Jugadores(
>>>>>>> c7dd6f7 (Corrección de errores.)
    id:Long,
    nombre:String,
    apellidos:String,
    fechaNacimiento: LocalDate,
    fechaIncorporacion: LocalDate,
    salario:Double,
    pais:String,
<<<<<<< HEAD
    val posicion:Posicion,
=======
    val posicion: Posicion,
>>>>>>> c7dd6f7 (Corrección de errores.)
    val dorsal:Int,
    val altura:Double,
    val peso:Double,
    val goles:Int,
    val partidosJugados:Int
<<<<<<< HEAD
):Persona(id, nombre, apellidos, fechaNacimiento, fechaIncorporacion, salario, pais){
    override fun toString(): String {
        return "id: $id,nombre: $nombre,apellidos: $apellidos,fecha_nacimiento: $fechaNacimiento," +
                "fecha_incorporacion: $fechaIncorporacion,salario: $salario,pais: $pais," +
                "posicion: $posicion,dorsal: $dorsal,altura: $altura,peso: $peso,goles: $goles," +
                "partidos_jugados: $partidosJugados"
    }
}
=======
):Persona(id, nombre, apellidos, fechaNacimiento, fechaIncorporacion, salario, pais)
>>>>>>> c7dd6f7 (Corrección de errores.)

enum class Posicion {
DELANTERO,CENTROCAMPISTA,PORTERO,DEFENSA
}
