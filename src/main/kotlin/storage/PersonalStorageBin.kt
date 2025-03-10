package storage

import dto.EntrenadorDto
import dto.JugadorDto
import exception.PersonasException
import mappers.PersonaMapper
import models.Entrenadores
import models.Jugadores
import models.Persona
import org.lighthousegames.logging.logging
import java.io.File
import java.io.RandomAccessFile


/**
 * Almacenamiento de personal en Bin
 * Esta clase implementa la interfaz [PersonalStorage] para almacenar y leer personal en un fichero Bin.
 */

class PersonalStorageBin : PersonalStorage {
    private val logger = logging()
    private val personalMapper = PersonaMapper()

    init {
        logger.debug { "Inicializando almacenamiento de personal en Bin" }
    }

    /**
     * Lee los empleados (Jugadores y Entrenadores) de un fichero Bin
     * @param file Fichero Bin
     * @return Lista de empleados
     * @throws PersonasException.PersonasStorageException Si el fichero no existe, no es un fichero o no se puede leer
     */
    override fun leerDelArchivo(file: File): List<Persona> {
        logger.debug { "Leyendo personal de fichero Bin: $file" }
        if (!file.exists() || !file.isFile || !file.canRead() || file.length() == 0L || !file.name.endsWith(".bin", true)) {
            logger.error { "El fichero no existe, o no es un fichero o no se puede leer: $file" }
            throw PersonasException.PersonasStorageException("El fichero no existe, o no es un fichero o no se puede leer: $file")
        }

        val personal = mutableListOf<Persona>()

        RandomAccessFile(file, "r").use { raf ->
            while (raf.filePointer < raf.length()) {
                val tipo = raf.readUTF() // Lee el tipo de persona (Jugador o Entrenador)
                val id = raf.readLong() // Lee el id
                val nombre = raf.readUTF() // Lee el nombre
                val apellidos = raf.readUTF() // Lee los apellidos
                val fechaNacimiento = raf.readUTF() // Lee la fecha de nacimiento
                val fechaIncorporacion = raf.readUTF() // Lee la fecha de incorporación
                val salario = raf.readDouble() // Lee el salario
                val pais = raf.readUTF() // Lee el país de origen

                if (tipo == "Jugador") {
                    val posicion = raf.readUTF() // Lee la posición
                    val dorsal = raf.readInt() // Lee el dorsal
                    val altura = raf.readDouble() // Lee la altura
                    val peso = raf.readDouble() // Lee el peso
                    val goles = raf.readInt() // Lee los goles
                    val partidosJugados = raf.readInt() // Lee los partidos jugados

                    val jugadorDto = JugadorDto(
                        id, nombre, apellidos, fechaNacimiento, fechaIncorporacion, salario, pais,
                        posicion, dorsal, altura, peso, goles, partidosJugados
                    )
                    personal.add(personalMapper.toModel(jugadorDto))
                } else if (tipo == "Entrenador") {
                    val especialidad = raf.readUTF() // Lee la especialidad

                    val entrenadorDto = EntrenadorDto(
                        id, nombre, apellidos, fechaNacimiento, fechaIncorporacion, salario, pais, especialidad
                    )
                    personal.add(personalMapper.toModel(entrenadorDto))
                }
            }
        }
        return personal
    }

    /**
     * Escribe los empleados (Jugadores y Entrenadores) en un fichero Bin
     * @param persona Lista de empleados
     * @param file Fichero bin
     * @throws PersonasException.PersonasStorageException Si el directorio padre del fichero no existe
     */
    override fun escribirAUnArchivo(file: File, persona: List<Persona>) {
        logger.debug { "Escribiendo personal en fichero Bin: $file" }
        if (!file.parentFile.exists() || !file.parentFile.isDirectory || !file.name.endsWith(".bin", true)) {
            logger.error { "El directorio padre del fichero no existe: ${file.parentFile.absolutePath}" }
            throw PersonasException.PersonasStorageException("El directorio padre del fichero no existe: ${file.parentFile.absolutePath}")
        }

        RandomAccessFile(file, "rw").use { raf ->
            raf.setLength(0) // Limpiar el archivo antes de escribir
            for (personal in persona) {
                when (personal) {
                    is Jugadores -> {
                        raf.writeUTF("Jugador")
                        raf.writeLong(personal.id)
                        raf.writeUTF(personal.nombre)
                        raf.writeUTF(personal.apellidos)
                        raf.writeUTF(personal.fechaNacimiento.toString())
                        raf.writeUTF(personal.fechaIncorporacion.toString())
                        raf.writeDouble(personal.salario)
                        raf.writeUTF(personal.pais)
                        raf.writeUTF(personal.posicion.name)
                        raf.writeInt(personal.dorsal)
                        raf.writeDouble(personal.altura)
                        raf.writeDouble(personal.peso)
                        raf.writeInt(personal.goles)
                        raf.writeInt(personal.partidosJugados)
                    }
                    is Entrenadores -> {
                        raf.writeUTF("Entrenador")
                        raf.writeLong(personal.id)
                        raf.writeUTF(personal.nombre)
                        raf.writeUTF(personal.apellidos)
                        raf.writeUTF(personal.fechaNacimiento.toString())
                        raf.writeUTF(personal.fechaIncorporacion.toString())
                        raf.writeDouble(personal.salario)
                        raf.writeUTF(personal.pais)
                        raf.writeUTF(personal.especialidad.name)
                    }
                }
            }
        }
    }
}
