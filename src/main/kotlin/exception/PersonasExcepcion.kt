package exception

sealed class PersonasExcepcion(message: String) : Exception(message) {

    class PersonasStorageExcepcion(message: String) : PersonasExcepcion(message)

}