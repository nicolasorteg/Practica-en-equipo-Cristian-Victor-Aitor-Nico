package config

import org.lighthousegames.logging.logging

import services.Tipo
import utils.toTipo
import java.io.FileNotFoundException
import java.nio.file.Path
import java.util.*
import kotlin.io.path.pathString




object Config {
    private val logger= logging()
    val configProperties: ConfiguracionProperties by lazy {
        loadConfig()
    }

    private fun loadConfig():ConfiguracionProperties {
        logger.debug { "cargando propiedades" }
        val properties = Properties()
        //busca el archivo de propiedades si no lo encuentra lanza una excepción
        val propertiesStream=this.javaClass.classLoader.getResourceAsStream("config.properties")
            ?: throw FileNotFoundException("config.properties")
        properties.load(propertiesStream)
        //crea las constantes en base al archivo properties para crear el objeto configuración
        val directorioUsado=System.getProperty("user.dir")
        val dataDirPropiedad=properties.getProperty("data.directory")?: "resources"
        val backupDirPropiedad=properties.getProperty("backup.directory")?: "data"
        val readFile=properties.getProperty("data.file")?: "personal.csv"
        val tipoFile=properties.getProperty("backup.Tipo").uppercase(Locale.getDefault()).toTipo()?: Tipo.CSV
        val dataDir= Path.of(directorioUsado,dataDirPropiedad).pathString
        val backupDir=Path.of(directorioUsado,backupDirPropiedad).pathString
        crearDirectorios(dataDir,backupDir)
        return ConfiguracionProperties(dataDir,backupDir,tipoFile,readFile)

    }

    private fun crearDirectorios(dataDir: String, backupDir: String) {
        logger.info { "creando directorios si no existen" }
        val data=java.io.File(dataDir)
        val backup=java.io.File(backupDir)
        if (!backup.exists()) {
            backup.mkdir()
        }
        if (!data.exists()) {
            data.mkdir()
        }
    }
}data class ConfiguracionProperties(val dataDir: String = "resources", val backupDir: String = "data", val tipo: Tipo =Tipo.CSV, val file: String ="personal.json")