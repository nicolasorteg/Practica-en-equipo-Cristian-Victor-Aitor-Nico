
package models

import java.time.LocalDate
import models.Persona
class Jugadores(
    id:Long,
    nombre:String,
    apellidos:String,
    fechaNacimiento: LocalDate,
    fechaIncorporacion: LocalDate,
    salario:Double,
    pais:String,
    val posicion:Posicion,
    val dorsal:Int,
    val altura:Double,
    val peso:Double,
    val goles:Int,
    val partidosJugados:Int
):Persona(id, nombre, apellidos, fechaNacimiento, fechaIncorporacion, salario, pais){
    override fun toString(): String {
        return "id: $id,nombre: $nombre,apellidos: $apellidos,fecha_nacimiento: $fechaNacimiento," +
                "fecha_incorporacion: $fechaIncorporacion,salario: $salario,pais: $pais," +
                "posicion: $posicion,dorsal: $dorsal,altura: $altura,peso: $peso,goles: $goles," +
                "partidos_jugados: $partidosJugados"
    }
}

enum class Posicion {
    DELANTERO,CENTROCAMPISTA,PORTERO,DEFENSA
}
