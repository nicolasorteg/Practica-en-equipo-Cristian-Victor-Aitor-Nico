package storage

import dto.EntrenadorDto
import dto.JugadorDto
import exception.PersonasException
import models.Persona
import mappers.PersonaMapper
import models.Entrenadores
import models.Jugadores
import nl.adaptivity.xmlutil.serialization.XML
import org.lighthousegames.logging.logging
import java.io.File
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.time.LocalDate

/**
 * Almacenamiento de personas en XML.
 * Esta clase implementa la interfaz para almacenar y leer personas en un fichero XML.
 */
class PersonalStorageXml {

    private val logger = logging()
    private val personaMapper = PersonaMapper() // Usamos el Mapper que has proporcionado

    init {
        logger.debug { "Inicializando almacenamiento de personas en XML" }
    }

    /**
     * Lee las personas desde un archivo XML.
     * @param file El archivo XML desde el que se leerán las personas.
     * @return Lista de personas (Jugadores o Entrenadores).
     * @throws PersonasException.PersonasStorageExcepcion Si el archivo no existe, no es un archivo o no se puede leer.
     */
    fun readFromFile(file: File): List<Persona> {
        logger.debug { "Leyendo personas de fichero XML: $file" }

        // Verificar que el archivo sea válido
        if (!file.exists() || !file.isFile || !file.canRead() || file.length() == 0L || !file.name.endsWith(".xml")) {
            logger.error { "El fichero no existe, o no es un fichero o no se puede leer: $file" }
            throw PersonasException.PersonasStorageException("El fichero no existe, o no es un fichero o no se puede leer: $file")
        }

        // Leer el archivo XML
        val xmlString = file.readText()
        val xml = XML {}

        // Parsear el XML a la clase correspondiente
        val equipoDto = xml.decodeFromString<EquipoDto>(xmlString)

        // Convertir cada persona en su modelo correspondiente
        return equipoDto.personal.map { personaDto ->
            when (personaDto.tipo) {
                "Jugador" -> personaMapper.toModel(JugadorDto(
                    id = personaDto.id,
                    nombre = personaDto.nombre,
                    apellidos = personaDto.apellidos,
                    fechaNacimiento = LocalDate.parse(personaDto.fechaNacimiento).toString(), // Convertir a LocalDate
                    fechaIncorporacion = LocalDate.parse(personaDto.fechaIncorporacion).toString(), // Convertir a LocalDate
                    salario = personaDto.salario.toDouble(), // Convertir a Double si es necesario
                    pais = personaDto.pais,
                    posicion = personaDto.posicion.toString(),
                    dorsal = personaDto.dorsal?.toInt() ?: 0 , // Convertir a Int si es necesario
                    altura = personaDto.altura?.toDouble() ?: 0.0, // Convertir a Double si es necesario
                    peso = personaDto.peso?.toDouble() ?: 0.0, // Convertir a Double si es necesario
                    goles = personaDto.goles?.toInt() ?: 0, // Convertir a Int si es necesario
                    partidosJugados = personaDto.partidosJugados?.toInt() ?: 0// Convertir a Int si es necesario
                ))
                "Entrenador" -> personaMapper.toModel(EntrenadorDto(
                    id = personaDto.id,
                    nombre = personaDto.nombre,
                    apellidos = personaDto.apellidos,
                    fechaNacimiento = LocalDate.parse(personaDto.fechaNacimiento).toString(), // Convertir a LocalDate
                    fechaIncorporacion = LocalDate.parse(personaDto.fechaIncorporacion).toString(), // Convertir a LocalDate
                    salario = personaDto.salario.toDouble(), // Convertir a Double si es necesario
                    pais = personaDto.pais,
                    especialidad = personaDto.especialidad.toString()
                ))
                else -> throw IllegalArgumentException("Tipo de persona desconocido en XML")
            }
        }

    }

    /**
     * Escribe las personas en un archivo XML.
     * @param personas La lista de personas (Jugadores o Entrenadores) que se escribirán.
     * @param file El archivo XML donde se guardarán las personas.
     * @throws PersonasException.PersonasStorageExcepcion Si el directorio no existe o no es válido.
     */
    fun writeToFile(personas: List<Persona>, file: File) {
        logger.debug { "Escribiendo personas en fichero XML: $file" }

        // Verificar que el archivo sea válido
        if (!file.parentFile.exists() || !file.parentFile.isDirectory || !file.name.endsWith(".xml")) {
            logger.error { "El directorio padre del fichero no existe: ${file.parentFile.absolutePath}" }
            throw PersonasException.PersonasStorageException("El directorio padre del fichero no existe: ${file.parentFile.absolutePath}")
        }

        // Convertir las personas a DTOs
        val equipoDto = EquipoDto(personas.map { persona ->
            when (persona) {
                is Jugadores -> PersonalDto(
                    id = persona.id,
                    tipo = "Jugador",
                    nombre = persona.nombre,
                    apellidos = persona.apellidos,
                    fechaNacimiento = persona.fechaNacimiento.toString(),
                    fechaIncorporacion = persona.fechaIncorporacion.toString(),
                    salario = persona.salario,
                    pais = persona.pais,
                    especialidad = "",
                    posicion = persona.posicion.name,
                    dorsal = persona.dorsal,
                    altura = persona.altura,
                    peso = persona.peso,
                    goles = persona.goles,
                    partidosJugados = persona.partidosJugados
                )
                is Entrenadores -> PersonalDto(
                    id = persona.id,
                    tipo = "Entrenador",
                    nombre = persona.nombre,
                    apellidos = persona.apellidos,
                    fechaNacimiento = persona.fechaNacimiento.toString(),
                    fechaIncorporacion = persona.fechaIncorporacion.toString(),
                    salario = persona.salario,
                    pais = persona.pais,
                    especialidad = persona.especialidad.name,
                    posicion = "",
                    dorsal = null,
                    altura = null,
                    peso = null,
                    goles = null,
                    partidosJugados = null
                )
                else -> throw IllegalArgumentException("Tipo de persona desconocido")
            }
        })

        // Escribir el archivo XML
        val xml = XML { indent = 4 }
        file.writeText(xml.encodeToString(equipoDto))
    }
}

/**
 * DTO que representa la estructura del XML de equipo.
 */
data class EquipoDto(val personal: List<PersonalDto>)

/**
 * DTO que representa cada persona (jugador o entrenador) dentro del XML.
 */
data class PersonalDto(
    val id: Long,
    val tipo: String,
    val nombre: String,
    val apellidos: String,
    val fechaNacimiento: String,
    val fechaIncorporacion: String,
    val salario: Double,
    val pais: String,
    val especialidad: String?,
    val posicion: String?,
    val dorsal: Int?,
    val altura: Double?,
    val peso: Double?,
    val goles: Int?,
    val partidosJugados: Int?
)
