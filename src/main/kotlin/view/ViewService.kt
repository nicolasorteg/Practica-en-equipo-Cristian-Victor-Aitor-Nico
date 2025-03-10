package view

import cache.Cache
import cache.CacheImplementationLru
import config.Config
import exception.PersonasException
import mappers.toLocalDate
import models.*
import repository.CrudPersonasImplementation
import services.PersonaServiceImplementation
import utils.*
import java.nio.file.Path
import java.util.*


class ViewService{
    private val repositorio=CrudPersonasImplementation()
    private val cache:Cache<Long,Persona> = CacheImplementationLru()
    private val configuracion=Config.configProperties
    private val controller=PersonaServiceImplementation(repositorio, cache)

    /**
     * Crea un menu para moverse entre las distintas zonas del programa
     */
    fun menu(){
        var salir = false
        while (!salir) {
            println("--- Sistema de Gestión de New Team ---")
            println("1. Cargar datos desde fichero")
            println("2. Crear miembro del equipo")
            println("3. Actualizar miembro del equipo")
            println("4. Eliminar miembro del equipo")
            println("5. Copiar datos a fichero")
            println("6. Realizar consultas")
            println("7. Salir")
            print("Seleccione una opción: ")

            when (readln().toIntOrNull()) {
                1 -> cargarDatosDesdeFichero()
                2 -> crearMiembro()
                3 -> actualizarMiembro()
                4 -> eliminarMiembro()
                5 -> copiarDatosAFichero()
                6 -> realizarConsultas()
                7 -> {
                    println("Saliendo del sistema...")
                    salir = true
                }
                else -> println("Opción no válida. Intente de nuevo.")
            }
        }
    }

    /**
     * carga los datos de un un archivo al repositorio
     */
    private fun cargarDatosDesdeFichero() {
        val productoFile= Path.of(configuracion.dataDir,configuracion.file)
        try {
            controller.importarDatosDesdeFichero(productoFile)
        }catch (e:PersonasException.PersonasStorageException){println(e.message)}
    }

    /**
     * Crea una nueva persona y la guarda en el repositorio
     */
    private fun crearMiembro() {

         println("¿Qué tipo de miembro deseas crear? (1) Jugador (2) Entrenador")
         val tipo = readln().toIntOrNull()

         if (tipo != 1 && tipo != 2) {
             println("Opción no válida. Debe ser 1 (Jugador) o 2 (Entrenador).")
             return
         }
         val id = 1L
         println("Introduce el nombre:")
         val nombre = readln().trim()

         println("Introduce los apellidos:")
         val apellidos = readln().trim()

         println("Introduce la fecha de nacimiento (YYYY-MM-DD):")
         val fechaNacimiento = readln().toLocalDate() ?: return println("fecha invalida")

            println("Introduce la fecha de incorporación (YYYY-MM-DD):")
            val fechaIncorporacion = readln().toLocalDate() ?: return println("fecha invalida")

            println("Introduce el salario:")
            val salario = readln().toDoubleOrNull() ?: return println("Salario inválido")

            println("Introduce el país:")
            val pais = readln().trim()

            val nuevoMiembro = when (tipo) {
                1 -> { // Jugador
                    println("Introduce la posición (DELANTERO, CENTROCAMPISTA, PORTERO, DEFENSA):")
                    val posicion = readln().toPosicion() ?: return println("posición invalidad")

                    println("Introduce el dorsal:")
                    val dorsal = readln().toIntOrNull() ?: return println("Dorsal inválido")

                    println("Introduce la altura en metros:")
                    val altura = readln().toDoubleOrNull() ?: return println("Altura inválida")

                    println("Introduce el peso en kg:")
                    val peso = readln().toDoubleOrNull() ?: return println("Peso inválido")

                    println("Introduce la cantidad de goles:")
                    val goles = readln().toIntOrNull() ?: 0

                    println("Introduce la cantidad de partidos jugados:")
                    val partidosJugados = readln().toIntOrNull() ?: 0

                    Jugadores(id, nombre, apellidos, fechaNacimiento, fechaIncorporacion, salario, pais, posicion, dorsal, altura, peso, goles, partidosJugados)
                }
                2 -> { // Entrenador
                    println("Introduce la especialidad (ENTRENADOR_PORTEROS, ENTRENADOR_ASISTENTE, ENTRENADOR_PRINCIPAL):")
                    val especialidad = Especialidad.valueOf(readln().uppercase())

                    Entrenadores(id, nombre, apellidos, fechaNacimiento, fechaIncorporacion, salario, pais, especialidad)
                }
                else -> return
            }
        try{
            controller.save(nuevoMiembro)
            println("Miembro creado con éxito.")
        } catch (e: Exception) {
            println("Error al crear el miembro: ${e.message}")
        }
    }

    /**
     * Actualiza los datos de una persona
     */
    private fun actualizarMiembro() {
            val id = preguntarId()
        try {
            val persona = controller.getByID(id)

            val listaDatos: Map<Int, String?>
            when (persona) {
                is Jugadores -> {
                    listaDatos = actualizarJugador()
                    val newPersona= persona.copy(listaDatos)
                    repositorio.save(newPersona)
                }
                is Entrenadores -> {
                    listaDatos = actualizarEntrenador()
                    val newPersona=persona.copy(listaDatos)
                    repositorio.save(newPersona)
                }
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    /**
     * Obtiene los datos a actualizar de un Entrenador
     * @return map con los datos a actualizar
     */
    private fun actualizarEntrenador(): Map<Int, String?> {
        val mapa= mutableMapOf<Int, String?>()
        do {
            println("que dato quieres cambiar")
            println()
            println("""0: salir
                |1: nombre
                |2: apellidos
                |3: salario
                |4: especialidad
            """.trimMargin())
            val numero= readln().toIntOrNull()?: 0
            if(numero in 1..3){
                try {
                    mapa[numero] = actualizarPersona(numero)
                }catch (e: IllegalArgumentException){println(e.message)}
            }
            when(numero){
                4->{
                    println("cual es la nueva especialidad(principal, asistente, porteros)")
                    try {
                        mapa[numero] = ("entrenador_" + readln()).toEspecialidad()?.let { this.toString() }
                        ?: run { mapa[numero]=null; throw IllegalArgumentException("solo se debe introducir principal, asistente, porteros") }
                        }catch (e: IllegalArgumentException){println(e.message)
                    }
                }
            }
        }while (numero in 1..4)
        return mapa
    }

    /**
     * Devuelve el dato a actualizar de una persona
     * @param numero ubicación del dato a obtener
     * @throws IllegalArgumentException cuando no se introducen los datos correctamente
     * @return dato a actualizar
     */
    private fun actualizarPersona(numero: Int): String? {
        when (numero) {
            1->{
                println("introduce el nuevo nombre")
                return readln()
            }
            2->{
                println("introduce el nuevo apellido")
                return readln()
            }
            3->{
                println("introduce el nuevo salario")
                readln().toDoubleOrNull()?.let { return this.toString() }?: run { throw IllegalArgumentException("no se ha introducido solo números") }
            }
        }
        return null
    }

    /**
     * devuelve un string con un dato en concreto de un jugador a actualizar
     * @param numero posición del dato a actualiza
     * @throws IllegalArgumentException cuando no se introducen los datos correctamente
     * @return el dato actualizado
     */
    private fun datosJugador(numero: Int): String? {
        when (numero) {
            4->{
                println("introduce la nueva posición (delantero, portero, centrocampista, defensa)")
                return readln().toPosicion()?.let { this.toString() }?: run { throw IllegalArgumentException("posición invalida, solo se pueden poner(delantero, centrocampista, defensa y portero)") }
            }
            5->{
                println("introduce el nuevo dorsal")
                return readln().toIntOrNull()?.let { this.toString() }?:run { throw IllegalArgumentException("dorsal invalido, solo se permiten números enteros") }
            }
            6->{
                println("introduce el nuevo peso")
                return readln().toDoubleOrNull()?.let { this.toString() }?:run { throw IllegalArgumentException("el peso solo puede contener números") }
            }
            7->{
                println("introduce el nuevo numero de goles")
                return readln().toIntOrNull()?.let { this.toString() }?:run { throw IllegalArgumentException("goles invalido solo se permiten números enteros") }
            }
            8->{
                println("introduce en nuevo numero de partidos jugados")
                return readln().toIntOrNull()?.let { this.toString() }?: run { throw IllegalArgumentException("partidos jugados solo permite números enteros") }
            }
        }
        return null
    }

    /**
     * Obtiene los datos a cambiar de un jugador
     * @return devuelve un mapa<Int,String?> con los datos a cambiar
     */
    private fun actualizarJugador(): Map<Int, String?> {
        val mapa= mutableMapOf<Int, String?>()
        do {
            println("que dato quieres cambiar")
            println()
            println("""0: salir
                |1: nombre
                |2: apellidos
                |3: salario
                |4: posición
                |5: dorsal
                |6: peso
                |7: numero de goles
                |8: numero de partidos jugados
            """.trimMargin())
            val numero= readln().toIntOrNull()?: 0
            if(numero in 1..3){
                try {
                    mapa[numero] = actualizarPersona(numero)
                }catch (e: IllegalArgumentException){println(e.message)}
            }else if(numero in 4..8){
                try {
                    mapa[numero] = datosJugador(numero)
                }catch (e: IllegalArgumentException){println(e.message)}
            }
        }while (numero in 1..8)
        return mapa
    }

    /**
     * Elimina un miembro según un ID proporcionado
     */
    private fun eliminarMiembro() {
        val id=preguntarId()
        try {
            controller.delete(id)
        }catch (e:Exception){println(e.message)  }
    }

    /**
     * Pregunta un, id por terminal
     * @return id proporcionada en Long
     */
    private fun preguntarId():Long {
        println("introduce la id del jugador")
        var id:Long
        do {
             id=readln().toLongOrNull()?: 0
            if (id<=0){
               println("valor incorrecto no puede ser negativo, o distinto a un numero")
            }
        }while (id<=0)
        return id
    }

    /**
     * Exporta una los datos del repositorio a un archivo
     */
    private fun copiarDatosAFichero() {
        val exportFile= Path.of(configuracion.backupDir,"personal.${configuracion.tipo.toStringFile()
            .lowercase(Locale.getDefault())}")
        try {
            controller.exportarDatosDesdeFichero(exportFile,configuracion.tipo)
        }catch (e:PersonasException.PersonasStorageException){println(e.message)}
    }

    private fun realizarConsultas(){
        controller.consultas()
    }
}
