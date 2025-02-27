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
    rol: Rol,
    val posicion:Posicion,
    val dorsal:Int,
    val altura:Double,
    val peso:Double,
    val goles:Int,
    val partidosJugados:Int
):Persona(id, nombre, apellidos, fechaNacimiento, fechaIncorporacion, salario, pais,rol){
    override fun toString(): String {
        return "id: $id,nombre: $nombre,apellidos: $apellidos,fecha_nacimiento: $fechaNacimiento," +
                "fecha_incorporacion: $fechaIncorporacion,salario: $salario,pais: $pais,rol: $rol," +
                "posicion: $posicion,dorsal: $dorsal,altura: $altura,peso: $peso,goles: $goles," +
                "partidos_jugados: $partidosJugados"
    }
}

enum class Posicion {
DELANTERO,CENTROCAMPISTA,PORTERO,DEFENSA
}
