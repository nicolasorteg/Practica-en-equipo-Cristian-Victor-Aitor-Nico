package view

import java.io.File

class ViewService(
    val files: List<File>,
) {
    val controller=Services()
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

    }

    fun crearMiembro() {

    }

    fun actualizarMiembro() {

    }

    fun eliminarMiembro() {

    }

    fun copiarDatosAFichero() {

    }
    fun realizarConsultas(){
        controller.consultas()
    }
}