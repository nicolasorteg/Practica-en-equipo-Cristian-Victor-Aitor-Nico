
package services

import cache.Cache
import exception.PersonasException
import models.Persona
import org.lighthousegames.logging.logging
import repository.CrudPersonasImplementation
import storage.PersonalStorageCsv
import storage.PersonalStorageJson
import storage.PersonalStorageXml
import validator.Validador
import java.nio.file.Path
import kotlin.io.path.name

class PersonaServiceImplementation(
    internal val repositorio:CrudPersonasImplementation,
    private val cache: Cache<Long, Persona>

):PersonaService {
    private val storageJson: PersonalStorageJson = PersonalStorageJson()
    private val storageCsv: PersonalStorageCsv = PersonalStorageCsv()
    private val storageXml: PersonalStorageXml = PersonalStorageXml()
    internal val logger= logging()
    override fun getAll(): List<Persona> {
        logger.info { "pasando a repositorio" }
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

    override fun importarDatosDesdeFichero(fichero: Path) {
        logger.info { "Seleccionando tipo" }
        val texto=fichero.name.split(".")
        when(texto[1]){
            "csv"->{
                val lista=storageCsv.leerDelArchivo(fichero.toFile())
                lista.forEach{repositorio.save(it)}
            }
            "json"->{
                val lista=storageJson.leerDelArchivo(fichero.toFile())
                lista.forEach{repositorio.save(it)}
            }
            "xml"->{
                val lista=storageXml.readFromFile(fichero.toFile())
                lista.forEach{repositorio.save(it)}

            }
        }
    }

    override fun exportarDatosDesdeFichero(fichero: Path, tipo: Tipo) {

        logger.info { "seleccionando tipo ${tipo.name}" }
        when(tipo){
            Tipo.CSV->storageCsv.escribirAUnArchivo(fichero.toFile(),repositorio.getAll())
            Tipo.JSON-> storageJson.escribirAUnArchivo(fichero.toFile(),repositorio.getAll())
            Tipo.XML -> storageXml.writeToFile(repositorio.getAll(),fichero.toFile())
            Tipo.BINARIO -> TODO()
        }
    }

}
enum class Tipo {
    CSV,JSON,XML,BINARIO
}
