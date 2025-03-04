package services

import cache.Cache
import exception.PersonasException
import models.Persona
import repository.CrudPersonasImplementation
import validator.Validador

class PersonaServiceImplementation(
    private val repositorio:CrudPersonasImplementation,
    private val cache: Cache<Long, Persona>
):PersonaService {
    override fun getAll(): List<Persona> {
        return repositorio.getAll()
    }

    override fun getByID(id: Long): Persona {
        val persona= cache[id]
        persona?.let { return persona }?:run {
            repositorio.getById(id)?.let {
                cache[id]=it
                return it
            }?:run {
                throw PersonasException.PersonaNotFoundException(id)
            }
        }
    }

    override fun save(persona: Persona): Persona {
        persona.Validador()
        return repositorio.save(persona)
    }

    override fun delete(id: Long): Persona {
        val eliminado= repositorio.delete(id) ?: throw PersonasException.PersonaNotFoundException(id)
        cache.remove(eliminado.id)
        return eliminado
    }

    override fun update(id: Long,persona: Persona): Persona {
        persona.Validador()
        val actualizado=repositorio.update(persona,id) ?: throw PersonasException.PersonaNotFoundException(id)
        cache[id] = actualizado
        return actualizado
    }
}