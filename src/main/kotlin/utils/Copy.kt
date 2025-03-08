package utils
import utils.toPosicion
import mappers.toLocalDate
import models.Entrenadores
import models.Especialidad
import models.Jugadores
import models.Posicion

fun Jugadores.copy(list: MutableMap<Long,String?>):Jugadores{
    return Jugadores(
        id=this.id,
        nombre=list[1]?:  run { this.nombre  },
        apellidos = list[2]?: run { this.apellidos },
        fechaNacimiento= (list[3]?.toLocalDate() ?: run { this.fechaNacimiento }),
        fechaIncorporacion= (list[4]?.toLocalDate() ?: run { this.fechaIncorporacion }),
        salario=list[5]?.toDoubleOrNull()?:run { this.salario },
        pais=list[6]?:run { this.pais },
        posicion= (list[7]?.toPosicion() ?: run { this.posicion }) as Posicion,
        dorsal=list[8]?.toIntOrNull()?:run { this.dorsal },
        altura=list[9]?.toDoubleOrNull()?: run { this.altura },
        peso=list[10]?.toDoubleOrNull()?: run { this.peso },
        goles=list[11]?.toIntOrNull()?: run { this.goles },
        partidosJugados=list[12]?.toIntOrNull()?:run { this.partidosJugados },
    )
}



fun Entrenadores.copy(list: MutableMap<Long,String?>):Entrenadores{
    return Entrenadores(
        id=this.id,
        nombre=list[1]?:  run { this.nombre },
        apellidos = list[2]?: run { this.apellidos },
        fechaNacimiento= (list[3]?.toLocalDate() ?: run { this.fechaNacimiento }),
        fechaIncorporacion= (list[4]?.toLocalDate() ?: run { this.fechaIncorporacion }),
        salario=list[5]?.toDoubleOrNull()?:run { this.salario },
        pais=list[6]?:run { this.pais },
        especialidad= (list[7]?.toEspecialidad() ?: run { this.especialidad }) as Especialidad,

    )
}


