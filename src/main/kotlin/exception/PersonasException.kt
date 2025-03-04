package exception

/**
 * Clase sellada que se encarga de almacenar los tipos de excepciones implementadas en el programa
 */
sealed class PersonasException(message: String) : Exception(message) {

    class PersonasStorageExcepcion(message: String) : PersonasException(message)

    class PersonaNotFoundException(id: Any) : PersonasException("Persona no encontrada con id: $id")
    class PersonaInvalidoException(message: String) : PersonasException("Persona no válida: $message")

}