package storage

import dto.EntrenadorDto
import dto.EquipoDtoXml
import dto.JugadorDto
import dto.PersonalDtoXml
import exception.PersonasException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import mappers.PersonaMapper
import models.Entrenadores
import models.Jugadores
import models.Persona
import nl.adaptivity.xmlutil.serialization.XML
import org.lighthousegames.logging.logging
import java.io.File

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
     * @throws PersonasException.PersonasStorageException Si el archivo no existe, no es un archivo o no se puede leer.
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
        val equipoDto = xml.decodeFromString<EquipoDtoXml>(xmlString)

        // Convertir cada persona en su modelo correspondiente
        return equipoDto.personal.map {
            when (it.tipo) {
                "Jugador" -> personaMapper.toModel(JugadorDto(
                    id = it.id.toLong(),
                    nombre = it.nombre,
                    apellidos = it.apellidos,
                    fechaNacimiento = it.fechaNacimiento, // Convertir a LocalDate
                    fechaIncorporacion = it.fechaIncorporacion, // Convertir a LocalDate
                    salario = it.salario, // Convertir a Double si es necesario
                    pais = it.pais,
                    posicion = it.posicion.toString(),
                    dorsal = it.dorsal?.toInt() ?: 0 , // Convertir a Int si es necesario
                    altura = it.altura?.toDouble() ?: 0.0, // Convertir a Double si es necesario
                    peso = it.peso?.toDouble() ?: 0.0, // Convertir a Double si es necesario
                    goles = it.goles?.toInt() ?: 0, // Convertir a Int si es necesario
                    partidosJugados = it.partidosJugados?.toInt() ?: 0// Convertir a Int si es necesario
                ))
                "Entrenador" -> personaMapper.toModel(EntrenadorDto(
                    id = it.id.toLong(),
                    nombre = it.nombre,
                    apellidos = it.apellidos,
                    fechaNacimiento = it.fechaNacimiento, // Convertir a LocalDate
                    fechaIncorporacion = it.fechaIncorporacion, // Convertir a LocalDate
                    salario = it.salario, // Convertir a Double si es necesario
                    pais = it.pais,
                    especialidad = it.especialidad.toString()
                ))
                else -> throw IllegalArgumentException("Tipo de persona desconocido en XML")
            }
        }

    }

    /**
     * Escribe las personas en un archivo XML.
     * @param personas La lista de personas (Jugadores o Entrenadores) que se escribirán.
     * @param file El archivo XML donde se guardarán las personas.
     * @throws PersonasException.PersonasStorageException Si el directorio no existe o no es válido.
     */
    fun writeToFile(personas: List<Persona>, file: File) {
        logger.debug { "Escribiendo personas en fichero XML: $file" }

        // Verificar que el archivo sea válido
        if (!file.parentFile.exists() || !file.parentFile.isDirectory || !file.name.endsWith(".xml")) {
            logger.error { "El directorio padre del fichero no existe: ${file.parentFile.absolutePath}" }
            throw PersonasException.PersonasStorageException("El directorio padre del fichero no existe: ${file.parentFile.absolutePath}")
        }

        // Convertir las personas a DTOs
        val equipoDto = EquipoDtoXml(personas.map { persona ->
            when (persona) {
                is Jugadores -> PersonalDtoXml(
                    id = persona.id.toInt(),
                    tipo = "Jugador",
                    nombre = persona.nombre,
                    apellidos = persona.apellidos,
                    fechaNacimiento = persona.fechaNacimiento.toString(),
                    fechaIncorporacion = persona.fechaIncorporacion.toString(),
                    salario = persona.salario,
                    pais = persona.pais,
                    especialidad = "",
                    posicion = persona.posicion.name,
                    dorsal = persona.dorsal.toString(),
                    altura = persona.altura.toString(),
                    peso = persona.peso.toString(),
                    goles = persona.goles.toString(),
                    partidosJugados = persona.partidosJugados.toString()
                )
                is Entrenadores -> PersonalDtoXml(
                    id = persona.id.toInt(),
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








