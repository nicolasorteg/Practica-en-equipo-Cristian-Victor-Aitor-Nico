package validator

import exception.PersonasException
import models.Jugadores
import models.Persona
import org.lighthousegames.logging.logging

/**
 * revisa si los datos introducidos en Persona{Jugadores/Entrenadores} son corrector
 * @throws personaInvalidoException
 * @receiver Persona
 */
fun Persona.Validador() {
    logger.debug { "validando persona" }
    val numeros:Regex = Regex("^*[0-9]*$")
   if(nombre.isBlank()){
       throw PersonasException.PersonasInvalidoException("el nombre esta en blanco")
   }
    if (nombre.length <= 2){
        throw PersonasException.PersonasInvalidoException("nombre invalido, demasiado corto")
    }
    if(nombre.contains(numeros)){
        throw PersonasException.PersonasInvalidoException("el nombre no pueden contener numeros")
    }
    if(apellidos.isBlank()){
        throw PersonasException.PersonasInvalidoException("los apellidos estan en blanco")
    }
    if (apellidos.length <= 2){
        throw PersonasException.PersonasInvalidoException("apellidos invalidos, demasiado cortos")
    }
    if(apellidos.contains(numeros)){
        throw PersonasException.PersonasInvalidoException("los apellidos no pueden contener numeros")
    }
    if (salario<=0){
        throw PersonasException.PersonasInvalidoException("salario invalido, salario negativo o igual a 0")
    }
    if(pais.isBlank()){
        throw PersonasException.PersonasInvalidoException("el pais esta en blanco")
    }
    if (pais.length <= 2){
        throw PersonasException.PersonasInvalidoException("pais invalido, demasiado corto")
    }
    if(pais.contains(numeros)){
        throw PersonasException.PersonasInvalidoException("el pais no pueden contener numeros")
    }
    //para comprobar si están correctos el resto de datos
    when(this) {
        is Jugadores -> {validarJugador(this)}
    }
}

/**
 * comprueba el resto de datos de jugadores
 * @param jugadores jugador a comprobar
 * @throws personaInvalidoException
 */
private fun validarJugador(jugadores: Jugadores) {
    val logger=logging()
    logger.debug { "validando jugador" }
    if (jugadores.dorsal<=0){
        throw PersonasException.PersonasInvalidoException("dorsal invalido, dorsal negativo")
    }
    if (jugadores.dorsal>99){
        throw PersonasException.PersonasInvalidoException("dorsal invalido, dorsal mayor a 99")
    }
    if (jugadores.altura<=0.5){
        throw PersonasException.PersonasInvalidoException("altura invalida, demasiado corto")
    }
    if(jugadores.altura>3.5){
        throw PersonasException.PersonasInvalidoException("altura invalido, demasiado alto")
    }
    if (jugadores.peso<=45){
        throw PersonasException.PersonasInvalidoException("peso invalido, desnutrido")
    }
    if (jugadores.peso>150){
        throw PersonasException.PersonasInvalidoException("peso invalido, tanque ruso")
    }
    if (jugadores.goles<0){
        throw PersonasException.PersonasInvalidoException("goles invalido, goles negativo")
    }
    if (jugadores.partidosJugados<0){
        throw PersonasException.PersonasInvalidoException("partidos jugados invalidos, partidos negativo")
    }
}

