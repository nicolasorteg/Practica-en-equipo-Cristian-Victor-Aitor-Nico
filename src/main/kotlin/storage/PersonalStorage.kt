package storage

import models.Persona
import java.io.File

/**
 * Interfaz que define las operaciones que se van a tener que realizar para gestionar correctamente todo el personal.
 */
interface PersonalStorage {

    /**
     * Lee la lista de personas desde un archivo de cualquier extensión.
     *
     * @param file Archivo del cual se leerán los datos.
     * @return Lista de objetos leídos.
     */
    fun leerDelArchivo (file:File): List<Persona>

    /**
     * Escribe una lista de personas en un archivo de la misma extensión que la del archivo leído.
     *
     * @param file Archivo en el cuál se guardará la nueva lista de personas.
     */
    fun escribirAUnArchivo (file: File, persona: List<Persona>)

}