package models

import java.time.LocalDate
<<<<<<< HEAD
import models.Persona

class Entrenadores(
=======

class Entrenadores(
    val especialidad: Especialidad,
>>>>>>> c7dd6f7 (Corrección de errores.)
    id:Long,
    nombre:String,
    apellidos:String,
    fechaNacimiento: LocalDate,
    fechaIncorporacion: LocalDate,
    salario:Double,
    pais:String,
<<<<<<< HEAD
    val especialidad:Especialidad
):Persona(id,nombre,apellidos,fechaNacimiento,fechaIncorporacion,salario,pais){
    override fun toString(): String {
        return "id: $id,nombre: $nombre,apellidos: $apellidos,fecha_nacimiento: $fechaNacimiento," +
                "fecha_incorporacion: $fechaIncorporacion,salario: $salario,pais: $pais," +
                "especialidad: $especialidad}"
    }

}
enum class Especialidad {
    ENTRENADOR_PORTEROS,ENTRENADOR_ASISTENTE,ENTRENADOR_PRINCIPAL
=======
):Persona(id,nombre,apellidos,fechaNacimiento,fechaIncorporacion,salario,pais){


}
enum class Especialidad {
PORTEROS,PRINCIPAL,ASISTENTE
>>>>>>> c7dd6f7 (Corrección de errores.)
}
