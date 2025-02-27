package models

import java.time.LocalDate

 abstract class Persona(
    val id:Long,
    val nombre:String,
    val apellidos:String,
    val fechaNacimiento:LocalDate,
    val fechaIncorporacion:LocalDate,
    val salario:Double,
<<<<<<< HEAD
    val pais:String,

=======
    val pais:String
>>>>>>> c7dd6f7 (Corrección de errores.)
){

}
