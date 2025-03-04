import models.Entrenadores
import models.Jugadores

val jugadores = mutableListOf<Jugadores>()
val entrenadores = mutableListOf<Entrenadores>()

fun main() {
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

fun realizarConsultas() {
    println("Consultas Disponibles: ")
    println("1. Listados de personal agrupados por entrenadores y jugadores")
    println("2. El delantero más alto")
    println("3. Media de goles de los delanteros")
    println("4. Defensa con más partidos jugados")
    println("5. Jugadores agrupados por su país de origen")
    println("6. Entrenador con el mayor salario")
    println("7. Promedio de altura de los jugadores agrupados por posición")
    println("8. Listado de todos los jugadores que han anotado más de 10 goles")
    println("9. Jugadores con un salario mayor al promedio del equipo")
    println("10. Número total de partidos jugados por todos los jugadores")
    println("11. Jugadores agrupados por el año de su incorporación al club")
    println("12. Entrenadores agrupados por su especialidad")
    println("13. Jugador más joven en el equipo")
    println("14. Promedio de peso de los jugadores por posición")
    println("15. Listado de todos los jugadores que tienen un dorsal par")
    println("16. Jugadores que han jugado menos de 5 partidos")
    println("17. Media de goles por partido de cada jugador")
    println("18. Listado de jugadores que tienen una altura superior a la media del equipo")
    println("19. Entrenadores que se incorporaron al club en los últimos 5 años")
    println("20. Jugadores que han anotado más goles que el promedio de su posición")
    println("21. Por posición, máximo de goles, mínimo de goles y media")
    println("22. Estimación del coste total de la plantilla")
    println("23. Total del salario pagado, agrupados por año de incorporación")
    println("24. Jugadores agrupados por país y, dentro de cada grupo, el jugador con más partidos jugados")
    println("25. Promedio de goles por posición, y dentro de cada posición, el jugador con el mayor número de goles")
    println("26. Entrenadores agrupados por especialidad, y dentro de cada especialidad, el entrenador con el salario más alto")
    println("27. Jugadores agrupados por década de nacimiento, y dentro de cada grupo, el promedio de partidos jugados")
    println("28. Salario promedio de los jugadores agrupados por su país de origen, y dentro de cada grupo, el jugador con el salario más bajo y alto")
    print("Seleccione una consulta: ")


    when (readlnOrNull()?.toIntOrNull()) {
        1 -> println(jugadores + entrenadores)
        2 -> println(jugadores.filter { it.posicion == "DELANTERO" }.maxByOrNull { it.altura })
        3 -> println(jugadores.filter { it.posicion == "DELANTERO" }.map { it.goles }.average())
        4 -> println(jugadores.filter { it.posicion == "DEFENSA" }.maxByOrNull { it.partidosJugados })
        5 -> println(jugadores.groupBy { it.pais })
        6 -> println(entrenadores.maxByOrNull { it.salario })
        7 -> println(jugadores.groupBy { it.posicion })
        8 -> println(jugadores.filter { it.goles > 10 })
        9 -> {
            val salarioPromedio = jugadores.map { it.salario }.average()
            println(jugadores.filter { it.salario > salarioPromedio })
        }
        10 -> println(jugadores.sumOf { it.partidosJugados })
        11 -> println(jugadores.groupBy { it.fechaIncorporacion.year })
        12 -> println(entrenadores.groupBy { it.rol })
        13 -> println(jugadores.minByOrNull { it.fechaNacimiento })
        14 -> println()
    }
}
