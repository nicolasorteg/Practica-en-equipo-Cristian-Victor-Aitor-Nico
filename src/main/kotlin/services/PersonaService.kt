package services

import models.Persona

interface PersonaService {
    fun getAll():List<Persona>
    fun getByID(id:Long):Persona
    fun save(persona: Persona): Persona
    fun delete(id: Long): Persona
    fun update(id: Long, persona: Persona): Persona
}