package view

import config.Config
import cache.Cache
import cache.CacheImplementationLru
import models.Entrenadores
import models.Jugadores
import services.PersonaServiceImplementation
import utils.consultas
import models.Persona
import models.Posicion
import repository.CrudPersonasImplementation
import java.nio.file.Path
import java.time.LocalDate
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
        try {
            println("¿Qué tipo de miembro deseas crear? (1) Jugador (2) Entrenador")
            val tipo = readln().toIntOrNull()

            if (tipo != 1 && tipo != 2) {
                println("Opción no válida. Debe ser 1 (Jugador) o 2 (Entrenador).")
                return
            }

            println("Introduce el ID:")
            val id = readln().toLongOrNull() ?: return println("ID inválido")

            println("Introduce el nombre:")
            val nombre = readln().trim()

            println("Introduce los apellidos:")
            val apellidos = readln().trim()

            println("Introduce la fecha de nacimiento (YYYY-MM-DD):")
            val fechaNacimiento = LocalDate.parse(readln())

            println("Introduce la fecha de incorporación (YYYY-MM-DD):")
            val fechaIncorporacion = LocalDate.parse(readln())

            println("Introduce el salario:")
            val salario = readln().toDoubleOrNull() ?: return println("Salario inválido")

            println("Introduce el país:")
            val pais = readln().trim()

            val nuevoMiembro = when (tipo) {
                1 -> { // Jugador
                    println("Introduce la posición (DELANTERO, CENTROCAMPISTA, PORTERO, DEFENSA):")
                    val posicion = Posicion.valueOf(readln().uppercase())

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

            controller.update(nuevoMiembro)
            println("Miembro creado con éxito.")

        } catch (e: Exception) {
            println("Error al crear el miembro: ${e.message}")
        }
    }
    private fun actualizarMiembro() {
        try {
            val id=preguntarId()
            val persona=controller.getByID(id)
            //falta por finalizar
        }catch (e:Exception){println(e.message)  }


    }

    private fun eliminarMiembro() {
        val id=preguntarId()
        controller.delete(id)
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
