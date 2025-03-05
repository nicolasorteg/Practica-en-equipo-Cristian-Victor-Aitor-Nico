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
        TODO()
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