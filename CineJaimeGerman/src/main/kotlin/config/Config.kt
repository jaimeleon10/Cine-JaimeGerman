package org.example.config

import org.lighthousegames.logging.logging
import java.nio.file.Files
import java.util.*
import kotlin.io.path.Path

private val logger = logging()

/**
 * Objeto Config donde cargaremos toda la configuracion
 * @property databaseUrl url de la base de datos
 * @property databaseInitTables true inicia tablas
 * @property databaseInMemory true crea la base de datos en memoria
 * @property storageData direccion de almacenamiento de storage
 * @property cacheSize tamaño de la cache
 * @property filas filas por defecto de nuestro cine
 * @property columnas columnas por defecto de nuestro cine
 * @author German Fernandez
 * @since 1.0.0
 */
object Config {
    var databaseUrl: String = "jdbc:sqlite:cineJaimeGerman.db"
        private set
    var databaseInitTables: Boolean = true
        private set
    var databaseInMemory: Boolean = false
        private set
    var storageData: String = "data"
        private set
    var cacheSize: Int = 5
        private set
    var filas: Int = 5
        private set
    var columnas: Int = 7
        private set

    init {
        try {

            logger.debug { "Cargando configuración DataBaseManager" }
            val properties = Properties()
            properties.load(ClassLoader.getSystemResourceAsStream("config.properties"))
            databaseUrl = properties.getProperty("database.url", databaseUrl)
            databaseInitTables = properties.getProperty("database.init.tables", databaseInitTables.toString()).toBoolean()
            databaseInMemory = properties.getProperty("database.inmemory", databaseInMemory.toString()).toBoolean()
            storageData = properties.getProperty("storage.data", storageData)
            cacheSize = properties.getProperty("cache.size", cacheSize.toString()).toInt()
            filas = properties.getProperty("filas", filas.toString()).toInt()
            columnas = properties.getProperty("columnas", columnas.toString()).toInt()
            logger.debug { "Configuración cargada correctamente" }

            // crear el directorio si no existe
            Files.createDirectories(Path(storageData))

        } catch (e: Exception) {
            logger.error { "Error cargando configuración: ${e.message}" }
            logger.error { "Usando valores por defecto" }
        }
    }
}