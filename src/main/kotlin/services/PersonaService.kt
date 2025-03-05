package services


import models.Persona
import java.nio.file.Path


interface PersonaService {
    fun getAll():List<Persona>
    fun getByID(id:Long):Persona
    fun save(persona: Persona): Persona
    fun delete(id: Long): Persona
    fun update(id: Long, persona: Persona): Persona
    fun importarDatosDesdeFichero(fichero: Path)
    fun exportarDatosDesdeFichero(fichero: Path,tipo: Tipo)
}