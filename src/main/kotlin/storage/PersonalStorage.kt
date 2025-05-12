package storage

import models.Persona
import java.io.File

interface PersonalStorage {
    fun leerDelArchivo (file:File): List<Persona>
    fun escribirAUnArchivo (file: File, persona: List<Persona>)

}