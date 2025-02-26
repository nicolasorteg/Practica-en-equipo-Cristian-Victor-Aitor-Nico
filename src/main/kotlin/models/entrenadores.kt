package models

import java.time.LocalDate
import models.Persona

class Entrenadores(
    val rol:Rol,
    id:Long,
    nombre:String,
    apellidos:String,
    fechaNacimiento: LocalDate,
    fechaIncorporacion: LocalDate,
    salario:Double,
    pais:String,
):Persona(id,nombre,apellidos,fechaNacimiento,fechaIncorporacion,salario,pais){


}
enum class Rol {
PORTEROS,PRINCIPAL,ASISTENTE
}
