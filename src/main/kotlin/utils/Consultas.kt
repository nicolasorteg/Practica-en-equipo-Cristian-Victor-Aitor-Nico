package utils

import models.Entrenadores
import models.Jugadores
import models.Posicion
import services.PersonaServiceImplementation
import java.time.LocalDate

fun PersonaServiceImplementation.consultas(){
    logger.debug{}
    val lista=repositorio.getAll()
    val jugadores= lista.filter { it is Jugadores }.map { it  as Jugadores}.toList()
    val entrenadores= lista.filter { it is Entrenadores }.map { it as Entrenadores }.toList()

    /**
     * Realizar las consultas
     */
    println("1. Listados de personal agrupados por entrenadores y jugadores")
    println(lista)

    println("2. El delantero más alto")
    println(jugadores.filter { it.posicion == Posicion.DELANTERO }.maxByOrNull { it.altura })

    println("3. Media de goles de los delanteros")
    println(jugadores.filter { it.posicion == Posicion.DELANTERO }.map { it.goles }.average())

    println("4. Defensa con más partidos jugados")
    println(jugadores.filter { it.posicion == Posicion.DEFENSA }.maxByOrNull { it.partidosJugados })

    println("5. Jugadores agrupados por su país de origen")
    println(jugadores.groupBy { it.pais })

    println("6. Entrenador con el mayor salario")
    println(entrenadores.maxByOrNull { it.salario })

    println("7. Promedio de altura de los jugadores agrupados por posición")
    jugadores.groupBy { it.posicion }.forEach { (posicion, lista) ->
        val promedioAltura = lista.map { it.altura }.average()
    println("$posicion: $promedioAltura") }

    println("8. Listado de todos los jugadores que han anotado más de 10 goles")
    println(jugadores.filter { it.goles > 10 })



    println("10. Número total de partidos jugados por todos los jugadores")
    println(jugadores.sumOf { it.partidosJugados })

    println("11. Jugadores agrupados por el año de su incorporación al club")
    println(jugadores.groupBy { it.fechaIncorporacion!!.year })

    println("12. Entrenadores agrupados por su especialidad")
    println(entrenadores.groupBy { it.especialidad })

    println("13. Jugador más joven en el equipo")
    println(jugadores.minByOrNull { it.fechaNacimiento!! })

    println("14. Promedio de peso de los jugadores por posición")
    jugadores.groupBy { it.posicion }.forEach { (posicion, lista) ->
        val promedioPeso = lista.map { it.peso }.average()
        println("$posicion: $promedioPeso")}

    println("15. Listado de todos los jugadores que tienen un dorsal par")
    println(jugadores.filter { it.dorsal.toInt() % 2 == 0 }.map { it.nombre + " " + it.apellidos })

    println("16. Jugadores que han jugado menos de 5 partidos")
    println(jugadores.filter { it.partidosJugados < 5 })

    println("17. Media de goles por partido de cada jugador:")
    jugadores.filter { it.partidosJugados > 0 }.map { it.nombre to it.goles.toDouble() / it.partidosJugados }.forEach { (nombre, media) ->
    println("$nombre: $media goles por partido")}



    println("19. Entrenadores que se incorporaron al club en los últimos 5 años")
    println(entrenadores.filter { (LocalDate.now().year - it.fechaIncorporacion!!.year) <=5 })





    println("22. Estimación del coste total de la plantilla")
    println(lista.sumOf { it.salario.toDouble() })

    println("23. Total del salario pagado, agrupados por año de incorporación")
    println(lista.filter { (it.fechaIncorporacion != null) && (it.salario != null) }.groupBy { it.fechaIncorporacion }
        .mapValues { (_, plantilla) -> plantilla.sumOf { it.salario }.toDouble() })

    println("24. Jugadores agrupados por país y, dentro de cada grupo, el jugador con más partidos jugados")
    println(jugadores.groupBy { it.pais }.mapValues { (_, jugadores) -> jugadores.maxByOrNull { it.partidosJugados } })



    println("26. Entrenadores agrupados por especialidad, y dentro de cada especialidad, el entrenador con el salario más alto")
    println(entrenadores.groupBy { it.especialidad }
        .mapValues { (_, entrenadores) -> entrenadores.maxByOrNull { it.salario.toDouble() } })



fun salarioPromedio(jugadores:List<Jugadores>): String {
    val salarioPromedio = jugadores.map { it.salario }.average()
    return jugadores.filter { it.salario > salarioPromedio}.toString() }

fun salarioPromedioPaisEstadisticas(jugadores:List<Jugadores>): Map<String, Triple<Double, Double?, Double?>> {
    return jugadores.groupBy { it.pais }
        .mapValues { (_, jugadores) ->
            val salarios = jugadores.map { it.salario.toDouble() }
            Triple(salarios.average(), salarios.minOrNull(), salarios.maxOrNull()) }
}

fun jugadoresPorDecada(jugadores:List<Jugadores>): Map<String, Double> {
    return jugadores.groupBy {
        val year = it.fechaNacimiento?.year ?: 0
        "${(year / 10) * 10}s" // falta añadir cosas para especificar lo de decada
    }.mapValues { (_, jugadores) -> jugadores.map { it.partidosJugados.toInt() }.average() }
}

fun listadoJugadoresAlturaSuperiorMedia(jugadores:List<Jugadores>): List<Jugadores> {
    val alturaMediaJugadores = jugadores.map { it.altura.toDouble() }.average()
    return jugadores.filter { it.altura > alturaMediaJugadores }
}

fun jugadoresSobrePromedio(jugadores:List<Jugadores>): List<Jugadores> {
    val promedioGolesPorPosicion = jugadores.filter { it.goles >= 0 }.groupBy { it.posicion }
        .mapValues { (_, jugadores) -> jugadores.map { it.goles }.average() }
    return jugadores.filter { it.goles > (promedioGolesPorPosicion[it.posicion] ?: 0.0) }
}

fun golesPorPosicion(jugadores:List<Jugadores>): Map<Posicion, Triple<Int?, Int?, Double>> {
    return jugadores.filter { it.goles >= 0 }
        .groupBy { it.posicion }
        .mapValues { (_, jugadores) ->
            val goles =
                jugadores.map { it.goles.toInt() }
            Triple(goles.maxOrNull(), goles.minOrNull(), goles.average())
        }
}

fun promedioGolesMaxGoleador(jugadores:List<Jugadores>) {
    val resultados = jugadores.groupBy { it.posicion }
        .mapValues { (_, jugadores) ->
            val promedioGoles = jugadores.map { it.goles }.average()
            val maxGoleador = jugadores.maxByOrNull { it.goles }
            Pair(promedioGoles, maxGoleador)
        }
    resultados.forEach { (posicion, datos) ->
        val (promedio, maxGoleador) = datos
        println("$posicion -> Promedio de goles: $promedio, Jugador con más goles: ${maxGoleador?.nombre} (${maxGoleador?.goles})")
    }
}}
