package dto

import java.time.LocalDate

abstract class PersonaDto (
    val id: Long,
    val nombre: String,
    val apellidos: String,
    val fechaNacimiento: LocalDate,
    val fechaIncorporacion: LocalDate,
    val salario: Double,
    val pais: String
)