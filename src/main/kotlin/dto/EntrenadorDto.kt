package dto

import java.time.LocalDate

/**
 * Clase que representa a los entrenadores.
 *
 * @param especialidad Tipo de entrenador.
 */
class EntrenadorDto(
    id: Long,
    nombre: String,
    apellidos: String,
    fechaNacimiento: LocalDate,
    fechaIncorporacion: LocalDate,
    salario: Double,
    pais: String,
    val especialidad: String
): PersonaDto(id, nombre, apellidos, fechaNacimiento, fechaIncorporacion, salario, pais)