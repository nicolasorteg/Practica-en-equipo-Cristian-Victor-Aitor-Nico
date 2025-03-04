package exception

import models.Persona

/**
 * Clase sellada que se encarga de almacenar los tipos de excepciones implementadas en el programa
 */
sealed class PersonasExcepcion(message: String) : Exception(message) {

    class PersonasStorageExcepcion(message: String) : PersonasExcepcion(message)

    class PersonaNotFoundException(id: Int) : PersonasExcepcion("Persona no encontrada con id: $id")
    class PersonasInvalidoException(message: String) : PersonasExcepcion("Persona no válida: $message")

}