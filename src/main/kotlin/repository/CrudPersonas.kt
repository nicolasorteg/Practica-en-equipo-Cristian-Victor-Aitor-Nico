package repository

import models.Persona

interface CrudPersonas:RepositoryCRUD<Persona, Long> {
    override fun getAll():List<Persona>
    override fun getById(id:Long):Persona?
    override fun update(objeto: Persona, id: Long): Persona?
    override fun delete(id: Long): Persona?
    override fun save(objeto: Persona): Persona
}