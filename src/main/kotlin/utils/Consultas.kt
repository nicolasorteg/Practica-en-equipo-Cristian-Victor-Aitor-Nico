package utils

import models.Entrenadores
import models.Jugadores
import services.PersonaServiceImplementation

fun PersonaServiceImplementation.consultas(){
    logger.debug{}
    val lista=repositorio.getAll()
    val jugadores= lista.map { it is Jugadores }.toList()
    val entrenadores= lista.map { it is Entrenadores }.toList()
}