package mappers

import dto.EntrenadorDto
import dto.JugadorDto
import dto.PersonaDto
import exception.PersonasException
import models.*
import java.time.LocalDate

/**
 * Clase Mapper que se encarga de convertir entre el modelo Jugador y el DTO JugadorDto.
 */
class PersonaMapper {

    // Convierte una Persona genérica a su correspondiente DTO (PersonaDto)
    fun toDto(persona: Persona): PersonaDto {
        return when (persona) {
            is Jugadores -> toJugadorDto(persona)
            is Entrenadores -> toEntrenadorDto(persona)
            else -> throw IllegalArgumentException("Tipo de persona no soportado")
        }
    }

    // Convierte un Jugador a JugadorDto
    private fun toJugadorDto(jugador: Jugadores): JugadorDto {
        return JugadorDto(
            id = jugador.id,
            nombre = jugador.nombre,
            apellidos = jugador.apellidos,
            fechaNacimiento = jugador.fechaNacimiento.toString(),
            fechaIncorporacion = jugador.fechaIncorporacion.toString(),
            salario = jugador.salario,
            pais = jugador.pais,
            posicion = jugador.posicion.name, // Usamos el valor del enum como String
            dorsal = jugador.dorsal,
            altura = jugador.altura,
            peso = jugador.peso,
            goles = jugador.goles,
            partidosJugados = jugador.partidosJugados
        )
    }

    // Convierte un Entrenador a EntrenadorDto
    private fun toEntrenadorDto(entrenador: Entrenadores): EntrenadorDto {
        return EntrenadorDto(
            id = entrenador.id,
            nombre = entrenador.nombre,
            apellidos = entrenador.apellidos,
            fechaNacimiento = entrenador.fechaNacimiento.toString(),
            fechaIncorporacion = entrenador.fechaIncorporacion.toString(),
            salario = entrenador.salario,
            pais = entrenador.pais,
            especialidad = entrenador.especialidad.name // Usamos el valor del enum como String
        )
    }

    // Convierte un JugadorDto a Jugador
    fun toModel(jugadorDto: JugadorDto): Jugadores {
        return Jugadores(
            id = jugadorDto.id,
            nombre = jugadorDto.nombre,
            apellidos = jugadorDto.apellidos,
            fechaNacimiento = toLocalDate() ?: run { throw PersonasException.PersonaInvalidoException("formato fecha invalido o incorrecto")},
            fechaIncorporacion = toLocalDate() ?: run { throw PersonasException.PersonaInvalidoException("formato fecha invalido o incorrecto")},
            salario = jugadorDto.salario,
            pais = jugadorDto.pais,
            posicion = Posicion.valueOf(jugadorDto.posicion), // Convertimos el String a enum
            dorsal = jugadorDto.dorsal,
            altura = jugadorDto.altura,
            peso = jugadorDto.peso,
            goles = jugadorDto.goles,
            partidosJugados = jugadorDto.partidosJugados
        )
    }

    // Convierte un EntrenadorDto a Entrenador
    fun toModel(entrenadorDto: EntrenadorDto): Entrenadores {
        return Entrenadores(
            especialidad = Especialidad.valueOf(entrenadorDto.especialidad), // Convertimos el String a enum
            id = entrenadorDto.id,
            nombre = entrenadorDto.nombre,
            apellidos = entrenadorDto.apellidos,
            fechaNacimiento = toLocalDate() ?: run { throw PersonasException.PersonaInvalidoException("formato fecha invalido o incorrecto")},
            fechaIncorporacion = toLocalDate() ?: run { throw PersonasException.PersonaInvalidoException("formato fecha invalido o incorrecto")},
            salario = entrenadorDto.salario,
            pais = entrenadorDto.pais
        )
    }
}

internal fun toLocalDate(): LocalDate? {
    TODO()
}
