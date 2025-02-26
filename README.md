# Gestión del equipo New Team
## Datos del Grupo
- Integrantes del Grupo: Aitor Aróstegui, Víctor Marín, Cristian Ortega y Nicolás Ortega.
- Usuarios de Github: @Aitoraros, @aragorn7372, @Cristianortegaa, @nicolasorteg


## Enunciado
El equipo New Team ha decido digitalizar su sistema de gestión de personal, que debe ser capaz de tratar la información de jugadores y entrenadores de manera eficiente.
* Gestion de Personal: estructura base que pueda ser usada para representar a cualquier empleado del club. Se debe incluir: id único y gestionado por el sistema de almacenamiento, nombre, apellidos, fecha de nacimiento, fecha de incorporación al bluc, salario y país de origen.
* Entrenadores: además de la inf. general, cada entrenador debe tener un campo adicional con su área de especializació. Las posibles especializaciones son: entrenador de porteros, entrenador asistente y entrenador principal.
* Jugadores: además de la inf. general, en cada jugador se debe incluir su posición en el campo, dorsal, altura, peso, nº de goles anotados y partidos jugados.

El servicio tiene una caché LRU de máx. 5 elementos para gestionar el personal con las operaciones CRUD. Los datos han de estar validados. Los datos pueden llegar por consola y ficheros en formato **CSV, XML, JSON y Binario** y que estos mismos formatos serán soportados para la salida de información, la cuál esta localizada según lo indicado en el fichero de configuración.

Hay un menú con las siguientes opciones
1. Cargar datos desde fichero según la especificación indicada.
2. Crear miembro del equipo.
3. Actualizar miembro de equipo.
4. Eliminar miembro del equipo.
5. Copiar datos a fichero según la especificación realizada.
6. Realizar consultas indicadas.

Además, en el servicio se incluyen consultas ejemplificativas para revisar que los datos se hayan tratado correctamente.




