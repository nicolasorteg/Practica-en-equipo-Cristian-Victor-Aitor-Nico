package dto

import java.time.LocalDate

class EntrenadorDto(
    id: Long,
    nombre: String,
    apellidos: String,
    fechaNacimiento: String,
    fechaIncorporacion: String,
    salario: Double,
    pais: String,
    val especialidad: String
) : PersonaDto(id, nombre, apellidos, fechaNacimiento, fechaIncorporacion, salario, pais)