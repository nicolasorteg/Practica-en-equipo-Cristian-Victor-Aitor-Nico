package validator

import models.Jugadores
import models.Persona
import org.lighthousegames.logging.logging
import exception.PersonasException

/**
 * Revisa si los datos introducidos en Persona{Jugadores/Entrenadores} son corrector
 * @throws PersonasException.PersonaInvalidoException
 * @receiver Persona
 */
fun Persona.validador() {
    logger.debug { "validando persona" }
    val numeros = Regex("^*[0-9]*$")
   if(nombre.isBlank()){
       throw PersonasException.PersonaInvalidoException("el nombre esta en blanco")
   }
    if (nombre.length <= 2){
        throw PersonasException.PersonaInvalidoException("nombre invalido, demasiado corto")
    }
    if(nombre.contains(numeros)){
        throw PersonasException.PersonaInvalidoException("el nombre no pueden contener numeros")
    }
    if(apellidos.isBlank()){
        throw PersonasException.PersonaInvalidoException("los apellidos están en blanco")
    }
    if (apellidos.length <= 2){
        throw PersonasException.PersonaInvalidoException("apellidos inválidos, demasiado cortos")
    }
    if(apellidos.contains(numeros)){
        throw PersonasException.PersonaInvalidoException("los apellidos no pueden contener números")
    }
    if (salario<=0){
        throw PersonasException.PersonaInvalidoException("salario invalido, salario negativo o igual a 0")
    }
    if(pais.isBlank()){
        throw PersonasException.PersonaInvalidoException("el pais esta en blanco")
    }
    if (pais.length <= 2){
        throw PersonasException.PersonaInvalidoException("pais invalido, demasiado corto")
    }
    if(pais.contains(numeros)){
        throw PersonasException.PersonaInvalidoException("el pais no pueden contener números")
    }
    //para comprobar si están correctos el resto de datos
    when(this) {
        is Jugadores -> {validarJugador(this)}
    }
}

/**
 * comprueba el resto de datos de jugadores
 * @param jugadores jugador a comprobar
 * @throws PersonasException.PersonaInvalidoException
 */
private fun validarJugador(jugadores: Jugadores) {
    val logger=logging()
    logger.debug { "validando jugador" }
    if (jugadores.dorsal<=0){
        throw PersonasException.PersonaInvalidoException("dorsal invalido, dorsal negativo")
    }
    if (jugadores.dorsal>99){
        throw PersonasException.PersonaInvalidoException("dorsal invalido, dorsal mayor a 99")
    }
    if (jugadores.altura<=0.5){
        throw PersonasException.PersonaInvalidoException("altura invalida, demasiado corto")
    }
    if(jugadores.altura>3.5){
        throw PersonasException.PersonaInvalidoException("altura invalido, demasiado alto")
    }
    if (jugadores.peso<=45){
        throw PersonasException.PersonaInvalidoException("peso invalido, desnutrido")
    }
    if (jugadores.peso>150){
        throw PersonasException.PersonaInvalidoException("peso invalido, tanque ruso")
    }
    if (jugadores.goles<0){
        throw PersonasException.PersonaInvalidoException("goles invalido, goles negativo")
    }
    if (jugadores.partidosJugados<0){
        throw PersonasException.PersonaInvalidoException("partidos jugados inválidos, partidos negativo")
    }
}

