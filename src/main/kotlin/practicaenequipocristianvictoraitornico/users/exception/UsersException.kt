package practicaenequipocristianvictoraitornico.users.exception

import practicaenequipocristianvictoraitornico.players.exception.PersonasException
import practicaenequipocristianvictoraitornico.users.service.UsersService

abstract class UsersException (val messager: String) {
    /**
     * Excepción que indica que no se ha encontrado el usuario buscado.
     *
     * @param message Mensaje de error.
     */
    class UsersNotFoundException(id: String): UsersException("Persona no encontrada con id: $id")
}