package dto

import java.time.LocalDate

/**
 * Clase DTO que representa a los jugadores.
 *
 * @param posicion Posición en la que juega el jugador.
 * @param dorsal Nº de camiseta del jugador.
 * @param altura Altura del jugador en metros.
 * @param peso Peso del jugador en kg.
 * @param goles Nº de goles metidos por el jugador.
 * @param partidosJugados Nº de partidos jugados por el jugador.
 */
class JugadorDto(
    id: Long,
    nombre: String,
    apellidos: String,
    fechaNacimiento: String,
    fechaIncorporacion: String,
    salario: Double,
    pais: String,
    val posicion: String,
    val dorsal: Int,
    val altura: Double,
    val peso: Double,
    val goles: Int,
    val partidosJugados: Int
): PersonaDto(id, nombre, apellidos, fechaNacimiento, fechaIncorporacion, salario, pais)