package storage

import dto.EntrenadorDto
import dto.JugadorDto
import exception.PersonasException
import models.Persona
import mappers.PersonaMapper
import models.Entrenadores
import models.Jugadores

import org.lighthousegames.logging.logging
import java.io.File
import java.time.LocalDate


/**
 * Esta es la implementación de la interfaz PersonalStorage.kt para leer y escribir datos de un listado de personas en formato JSON.
 */
class PersonalStorageJson : PersonalStorage {

    // Logger para registrar los eventos que suceden en la clase
    private val logger = logging()

    // Instancia del Mapper que se encarga de la conversión entre modelos y DTOs
    private val personaMapper = PersonaMapper()


    /**
     * Esta función lee las personas de un archivo JSON y las devuelve como una lista de objetos Persona.
     *
     * @param file Este es el archivo JSON desde el cual se leen los datos.
     * @return Devuelve la lista de personas leídas del archivo.
     * @throws PersonasException.PersonasStorageException Si el archivo no existe, no se puede leer, o tiene un formato incorrecto.
     */
    override fun leerDelArchivo (file:File): List<Persona> {

        logger.debug { "Leyendo personas de fichero JSON: $file" }

        // verificación de que el archivo sea válido antes de intentar leerlo
        if (!file.exists() || !file.isFile || !file.canRead() || file.length() == 0L || !file.name.endsWith(".json")) {
            logger.error { "El fichero no existe, o no es un fichero o no se puede leer: $file" }
            throw PersonasException.PersonasStorageException("El fichero no existe, o no es un fichero o no se puede leer: $file")
        }


        val jsonContent = file.readText() // lectura el contenido del archivo JSON

        val personas = mutableListOf<Persona>()

        val personasJson = jsonContent.trim().removeSurrounding("[", "]").split("}, {")


        for (personaJson in personasJson) {
            val json = personaJson
                .removeSurrounding("{", "}")
                .split(",")
                .map { it.split(":") }
                .associate { it[0].trim().removeSurrounding("\"") to it[1].trim().removeSurrounding("\"") }

            // dependiendo del rol de cada persona, se crea el DTO correspondiente
            when (json["tipo"]) {

                "Jugador" -> {
                    // creación el DTO de Jugador
                    val jugadorDto = JugadorDto(
                        id = json["id"]?.toLong() ?: 0,
                        nombre = json["nombre"] ?: "",
                        apellidos = json["apellidos"] ?: "",
                        fechaNacimiento = LocalDate.parse(json["fechaNacimiento"] ?: ""),
                        fechaIncorporacion = LocalDate.parse(json["fechaIncorporacion"] ?: ""),
                        salario = json["salario"]?.toDouble() ?: 0.0,
                        pais = json["pais"] ?: "",
                        posicion = json["posicion"] ?: "",
                        dorsal = json["dorsal"]?.toInt() ?: 0,
                        altura = json["altura"]?.toDouble() ?: 0.0,
                        peso = json["peso"]?.toDouble() ?: 0.0,
                        goles = json["goles"]?.toInt() ?: 0,
                        partidosJugados = json["partidosJugados"]?.toInt() ?: 0
                    )
                    // conversión del DTO a modelo y se agrega a la lista
                    personas.add(personaMapper.toModel(jugadorDto))


                }
                "Entrenador" -> {
                    // creación del DTO de entrenador
                    val entrenadorDto = EntrenadorDto(
                        id = json["id"]?.toLong() ?: 0,
                        nombre = json["nombre"] ?: "",
                        apellidos = json["apellidos"] ?: "",
                        fechaNacimiento = LocalDate.parse(json["fechaNacimiento"] ?: ""),
                        fechaIncorporacion = LocalDate.parse(json["fechaIncorporacion"] ?: ""),
                        salario = json["salario"]?.toDouble() ?: 0.0,
                        pais = json["pais"] ?: "",
                        especialidad = json["especialidad"] ?: ""
                    )

                    // conversión del DTO a modelo y se agrega a la lista
                    personas.add(personaMapper.toModel(entrenadorDto))
                }
                else -> { // si no es ni jugador ni entrenador = excepción
                    throw IllegalArgumentException("Tipo de persona desconocido en JSON")
                }
            }
        }

        return personas
    }

    /**
     * Se encarga de escribir una lista de personas en un archivo JSON.
     *
     * @param file El archivo JSON donde escribir los datos.
     * @param persona La lista de personas a escribir en el archivo.
     * @throws PersonasException.PersonasStorageException Si el archivo no es válido o no se puede escribir.
     */
    override fun escribirAUnArchivo (file: File, persona: List<Persona>) {
        // mensaje de debug para la escritura del archivo
        logger.debug { "Escribiendo personas en fichero JSON: $file" }

        // verificación de que el directorio del archivo existe y es válido
        if (!file.parentFile.exists() || !file.parentFile.isDirectory || !file.name.endsWith(".json")) { // si no existe, no está o no es de la extensión .json
            logger.error { "El directorio padre del fichero no existe: ${file.parentFile.absolutePath}" }
            throw PersonasException.PersonasStorageException("El directorio padre del fichero no existe: ${file.parentFile.absolutePath}")
        }

        // creación del JSON manualmente como String
        val json = persona.joinToString(prefix = "[", postfix = "]") { persona ->
            when (persona) {

                // casting
                is Jugadores -> """ 
                    {
                        "tipo": "Jugador",
                        "id": "${persona.id}",
                        "nombre": "${persona.nombre}",
                        "apellidos": "${persona.apellidos}",
                        "fechaNacimiento": "${persona.fechaNacimiento}",
                        "fechaIncorporacion": "${persona.fechaIncorporacion}",
                        "salario": "${persona.salario}",
                        "pais": "${persona.pais}",
                        "posicion": "${persona.posicion.name}",
                        "dorsal": "${persona.dorsal}",
                        "altura": "${persona.altura}",
                        "peso": "${persona.peso}",
                        "goles": "${persona.goles}",
                        "partidosJugados": "${persona.partidosJugados}"
                    }
                """

                is Entrenadores -> """
                    {
                        "tipo": "Entrenador",
                        "id": "${persona.id}",
                        "nombre": "${persona.nombre}",
                        "apellidos": "${persona.apellidos}",
                        "fechaNacimiento": "${persona.fechaNacimiento}",
                        "fechaIncorporacion": "${persona.fechaIncorporacion}",
                        "salario": "${persona.salario}",
                        "pais": "${persona.pais}",
                        "especialidad": "${persona.especialidad.name}"
                    }
                """
                else -> ""
            }
        }

        // escritura del JSON en el archivo
        file.writeText(json)
    }
}
