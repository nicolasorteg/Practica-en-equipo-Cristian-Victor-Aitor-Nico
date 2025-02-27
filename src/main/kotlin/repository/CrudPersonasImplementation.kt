package repository

import models.Entrenadores
import models.Jugadores
import models.Persona
import org.lighthousegames.logging.logging

/**
 * Implementa las funciones de CRUD especializando los tipos
 */
class CrudPersonasImplementation:CrudPersonas {
    private var personas = mutableMapOf<Long, Persona>()
    private var logger= logging()
    /**
     * Devuelve una lista con todas las personas
     * @return lista con personas
     */
    override fun getAll(): List<Persona> {
        return personas.values.toList()
    }

    /**
     * devuelve una persona en base a un id
     * @param id de la persona a encontrar
     * @return Persona encontrada o nulo si no se encuentra
     */
    override fun getById(id: Long): Persona? {
        return personas[id]
    }

    /**
     * Actualiza los datos de una persona
     * @param objeto son los datos nuevos de la persona
     * @param id para encontrar al original
     * @return devuelve la persona actualizada o nulo si no se encuentra
     */
    override fun update(objeto: Persona, id: Long): Persona? {
        val personaNueva: Persona = if (objeto is Jugadores) {
            jugadorCopy(objeto, id)
        } else {
            entrenadorCopy(objeto as Entrenadores, id)
        }
        personas[id] = personaNueva
        return personas[id]
    }

    /**
     * elimina la persona seleccionada de la lista
     * @param id para encontrar a la persona
     * @return devuelve nulo si no se encuentra o la persona si se encuentra
     */
    override fun delete(id: Long): Persona? {
        return personas.remove(id)
    }

    /**
     * Guarda una nueva persona en la lista
     * @param objeto persona a guardar
     * @return devuelve el objeto guardado
     */
    override fun save(objeto: Persona): Persona {
        val personaNueva: Persona
        val id = personas.keys.maxOrNull()?.plus(1) ?: 1
        personaNueva = if (objeto is Jugadores) {
            jugadorCopy(objeto, id)
        } else {
            entrenadorCopy(objeto as Entrenadores, id)
        }
        personas[id] = personaNueva
        return personas[id]!!
    }

    /**
     * Copia un entrenador con una, id adecuada
     * @param persona persona a copiar
     * @param id id que hay que darle
     * @return devuelve el entrenador actualizado
     */
    private fun entrenadorCopy(persona: Entrenadores, id: Long): Entrenadores {
        val entrenador=Entrenadores(
            persona.rol,
            id,
            persona.nombre,
            persona.apellidos,
            persona.fechaNacimiento,
            persona.fechaIncorporacion,
            persona.salario,
            persona.pais,
        )
        return entrenador
    }

    /**
     * Copia un entrenador con una, id adecuada
     * @param persona persona a copiar
     * @param id id que hay que darle
     * @return devuelve el entrenador actualizado
     */
    private fun jugadorCopy(persona: Jugadores, id: Long): Jugadores {
        val jugador = Jugadores(
            id,
            persona.nombre,
            persona.apellidos,
            persona.fechaNacimiento,
            persona.fechaIncorporacion,
            persona.salario,
            persona.pais,
            persona.especialidad,
            persona.posicion,
            persona.dorsal,
            persona.altura,
            persona.peso,
            persona.goles,
            persona.partidosJugados
        )
        return jugador
    }
}

