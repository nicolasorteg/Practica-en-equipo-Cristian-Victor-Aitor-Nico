package view

import cache.Cache
import cache.CacheImplementationLru
import config.Config
import mappers.toLocalDate
import models.*
import repository.CrudPersonasImplementation
import services.PersonaServiceImplementation
import utils.consultas
import utils.toEspecialidad
import utils.toPosicion
import java.nio.file.Path
import java.util.*


class ViewService{
    private val repositorio=CrudPersonasImplementation()
    private val cache:Cache<Long,Persona> = CacheImplementationLru()
    private val configuracion=Config.configProperties
    private val controller=PersonaServiceImplementation(repositorio, cache)

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
    fun cargarDatosDesdeFichero() {
        val productoFile= Path.of(configuracion.dataDir,configuracion.file)
        controller.importarDatosDesdeFichero(productoFile)
    }

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
            val fechaNacimiento = readln().toLocalDate()

            println("Introduce la fecha de incorporación (YYYY-MM-DD):")
            val fechaIncorporacion = readln().toLocalDate()

            println("Introduce el salario:")
            val salario = readln().toDoubleOrNull() ?: return println("Salario inválido")

            println("Introduce el país:")
            val pais = readln().trim()

            val nuevoMiembro = when (tipo) {
                1 -> { // Jugador
                    println("Introduce la posición (DELANTERO, CENTROCAMPISTA, PORTERO, DEFENSA):")
                    val posicion = readln().toPosicion() ?: return println("posicion invalidad")

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
    private fun actualizarMiembro() {
            val id = preguntarId()
        try {
            val persona = controller.getByID(id)
            val listaDatos: Map<Int, String?>
            when (persona) {
                is Jugadores -> {
                    listaDatos = actualizarJugador(persona as Jugadores)
                }

                is Entrenadores -> {
                    listaDatos = actualizarEntrenador(persona as Entrenadores)
                }
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun actualizarEntrenador(entrenadores: Entrenadores): Map<Int, String?> {
        val mapa= mutableMapOf<Int, String?>()
        do {
            println("que dato quieres cambiar")
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
        return mapa as Map<Int, String?>
    }

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
    val posicion: Posicion,
    val dorsal:Int,
    val altura:Double,
    val peso:Double,
    val goles:Int,
    val partidosJugados:Int
    private fun DatosJugador(numero: Int): String? {

    }

    private fun actualizarJugador(jugadores: Jugadores): Map<Int, String?> {
        val mapa= mutableMapOf<Int, String?>()
        do {
            println("que dato quieres cambiar")
            val numero= readln().toIntOrNull()?: 0
            if(numero in 1..3){
                try {
                    mapa[numero] = actualizarPersona(numero)
                }catch (e: IllegalArgumentException){println(e.message)}
            }else if(numero in 4..9){
                try {
                    mapa[numero] = DatosJugador(numero)
                }catch (e: IllegalArgumentException){println(e.message)}
            }

            }
        }while (numero in 1..9)
        return mapa as Map<Int, String?>
    }

    private fun eliminarMiembro() {
        val id=preguntarId()
        try {
            controller.delete(id)
        }catch (e:Exception){println(e.message)  }
    }

    private fun preguntarId():Long {
        println("introduce la id del jugador a eliminar")
        var id:Long
        do {
             id=readln().toLongOrNull()?: 0
            if (id<=0){
               println("valor incorrecto no puede ser negativo, o distinto a un numero")
            }
        }while (id<=0)
        return id
    }

    private fun copiarDatosAFichero() {
        val exportFile= Path.of(configuracion.backupDir,"personal.${configuracion.tipo.toString()
            .lowercase(Locale.getDefault())}")
        controller.exportarDatosDesdeFichero(exportFile,configuracion.tipo)
    }

    private fun realizarConsultas(){
        controller.consultas()
    }
}
