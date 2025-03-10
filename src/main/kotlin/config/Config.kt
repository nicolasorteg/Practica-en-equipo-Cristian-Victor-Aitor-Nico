package config

import org.lighthousegames.logging.logging

import services.Tipo
import utils.toTipo
import java.io.File
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
        //crea las constantes basándonos en el archivo properties para crear el objeto configuración
        val stringDateConf:String = properties.getProperty("local.time")?:"en_EN"
        val directorioUsado=System.getProperty("user.dir")
        val dataDirPropiedad=properties.getProperty("data.directory")?: "data"
        val backupDirPropiedad=properties.getProperty("backup.directory")?: "backups"
        val readFile=properties.getProperty("data.file")?: "data/personal.csv"
        val tipoFile= properties.getProperty("backup.Tipo")?.toTipo()?: Tipo.CSV
        val dataDir= Path.of(directorioUsado,dataDirPropiedad).pathString
        val backupDir=Path.of(directorioUsado,backupDirPropiedad).pathString
        crearDirectorios(dataDir,backupDir)
        return ConfiguracionProperties(dataDir,backupDir,tipoFile,readFile,stringDateConf)

    }
    private fun crearArchivo(backupDir: String,tipoFile: File){
        logger.info { "creando archivo si no existe" }
        val archivo=java.io.File(backupDir,"personal."+ tipoFile.toString().lowercase(Locale.getDefault()))

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
}data class ConfiguracionProperties(val dataDir: String = "resources", val backupDir: String = "data", val tipo: Tipo =Tipo.CSV, val file: String ="personal.json", val localTime:String)