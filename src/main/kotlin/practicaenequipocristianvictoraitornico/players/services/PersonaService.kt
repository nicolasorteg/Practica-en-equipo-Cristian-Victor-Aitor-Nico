package practicaenequipocristianvictoraitornico.players.services

import com.github.michaelbull.result.Result
import practicaenequipocristianvictoraitornico.common.service.Service
import practicaenequipocristianvictoraitornico.players.exception.PersonasException
import practicaenequipocristianvictoraitornico.players.models.Persona
import java.nio.file.Path

interface PersonaService: Service<Persona, PersonasException, Long> {
    fun importarDatosDesdeFichero(fichero: Path): Result<List<Persona>, PersonasException>
    fun exportarDatosDesdeFichero(fichero: Path, tipo: Tipo): Result<String, PersonasException>
}