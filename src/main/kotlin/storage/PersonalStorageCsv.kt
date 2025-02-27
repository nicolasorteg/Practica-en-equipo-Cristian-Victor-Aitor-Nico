package storage

import models.Persona
import org.lighthousegames.logging.logging

import java.io.File

class PersonalStorageCsv: PersonalStorage {

    private val logger = logging()

    override fun leerDelArchivo(file: File): List<Persona> {

        val file = File("resources", "personal.csv")



    }

    override fun escribirAUnArchivo(file: File, persona: List<Persona>) {

    }


}