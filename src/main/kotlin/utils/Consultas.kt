package consultas

import models.Jugadores
import services.PersonaServiceImplementation

fun PersonaServiceImplementation.consultas(){
    logger.debug{}
    val lista=repositorio.getAll()
    val jugadores= lista.map { it is Jugadores }
    val
}