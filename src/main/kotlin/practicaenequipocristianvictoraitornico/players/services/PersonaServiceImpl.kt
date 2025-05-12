package practicaenequipocristianvictoraitornico.players.services

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.lighthousegames.logging.logging
import practicaenequipocristianvictoraitornico.players.exception.PersonasException
import practicaenequipocristianvictoraitornico.players.models.Persona
import practicaenequipocristianvictoraitornico.players.repository.PersonasRepositoryImplementation
import practicaenequipocristianvictoraitornico.players.storage.PersonalStorageZip
import practicaenequipocristianvictoraitornico.players.storage.Tipo
import practicaenequipocristianvictoraitornico.players.validator.PersonaValidation
import java.nio.file.Path

class PersonaServiceImpl(
    private val repositorio: PersonasRepositoryImplementation,
    private val cache: Cache<Long,Persona>,
    private val validator: PersonaValidation,
    private val storage: PersonalStorageZip
):PersonaService {
    private val logger= logging()
    override fun importarDatosDesdeFichero(fichero: Path): Result<List<Persona>, PersonasException> {
        logger.debug { "importando desde fichero" }
        val archivo= fichero.toFile()
        val lista= storage.leerDelArchivo(archivo)
        if (lista.isOk){
            lista.value.forEach {
                val validado=validator.validator(it)
                if (validado.isOk) repositorio.save(it) else return Err(validado.error)
            }
        }else return Err(lista.error)
        return lista
    }

    override fun exportarDatosDesdeFichero(fichero: Path, tipo: Tipo): Result<String, PersonasException> {
        logger.debug { "exportando desde fichero" }
        val archivo= fichero.toFile()
        val lista= getAll()
        val result= storage.escribirAUnArchivo(archivo,lista,tipo)
        if (result.isOk){
            return Ok(result.value)
        }
        return Err(result.error)
    }

    override fun getAll(): List<Persona> {
        return repositorio.getAll()
    }

    override fun getByID(id: Long): Result<Persona, PersonasException> {
        return cache.getIfPresent(id)?.let { Ok(it) }?:run {
            repositorio.getById(id)?.let {
                cache.put(id,it)
                Ok(it)
            }?:run {
                Err(PersonasException.PersonaNotFoundException(id))
            }
        }
    }

    override fun save(item: Persona): Result<Persona, PersonasException> {
        val validado=validator.validator(item)
        return if (validado.isOk){
            Ok(repositorio.save(item))
        } else Err(validado.error)
    }

    override fun delete(id: Long): Result<Persona, PersonasException> {
        return repositorio.delete(id)?.let {
            cache.getIfPresent(id)?.let { cache.invalidate(id) }
            return Ok(it)
        }?: Err(PersonasException.PersonaNotFoundException(id))
    }

    override fun update(id: Long, item: Persona): Result<Persona, PersonasException> {
        val validado=validator.validator(item)
        if (validado.isOk){
            repositorio.update(item,id)?.let { it ->
                cache.getIfPresent(id)?.let {
                    cache.invalidate(id)
                cache.put(id,it)
            }
            return Ok(it)
            }?: return Err(PersonasException.PersonaNotFoundException(id))
        }
        return Err(validado.error)
    }

}