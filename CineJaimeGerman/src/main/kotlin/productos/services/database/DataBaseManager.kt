package org.example.productos.services.database

import org.example.config.Config
import org.apache.ibatis.jdbc.ScriptRunner
import org.lighthousegames.logging.logging
import java.io.PrintWriter
import java.io.Reader
import java.sql.Connection
import java.sql.DriverManager

private val logger = logging()

/**
 * Objecto databaseManager
 * Manejador de la base de datos para productos
 * @since 1.0.0
 * @author Jaime leon
 */
object DataBaseManager : AutoCloseable {
    var connection: Connection? = null
        private set

    /**
     * Inicializamos la base de datos
     * @since 1.0.0
     * @author Jaime leon
     */
    init {
        // Iniciamos la base de datos
        initConexion()
        if (Config.databaseInitTables) {
            initTablas()
        }
    }

    /**
     * Función para iniciar la conexion, tablas y datos de ejemplo para los tests
     * @author Jaime Leon
     * @since 1.0.0
     */
    fun initTests(){
        initConexion()
        initTablas()

        val data = ClassLoader.getSystemResourceAsStream("data.sql")?.bufferedReader()!!
        scriptRunner(data, false)
    }

    /**
     * Inicializamos las tablas de la base de datos en caso de que se haya configurado
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun initTablas() {
        logger.debug { "Creando tablas" }
        try {
            val tablas = ClassLoader.getSystemResourceAsStream("tables.sql")?.bufferedReader()!!
            scriptRunner(tablas, false)
            logger.debug { "Tablas creadas" }
        } catch (e: Exception) {
            logger.error { "Error al crear las tablas: ${e.message}" }
        }
    }

    /**
     * Inicializamos la conexión con la base de datos
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun initConexion() {
        // Inicializamos la base de datos
        logger.debug { "Iniciando conexión con la base de datos" }
        if (connection == null || connection!!.isClosed) {
            connection = DriverManager.getConnection(Config.databaseUrl)
        }
        logger.debug { "Conexión con la base de datos inicializada" }
    }


    /**
     * Cerramos la conexión con la base de datos
     * @author Jaime Leon
     * @since 1.0.0
     */
    override fun close() {
        logger.debug { "Cerrando conexión con la base de datos" }
        if (!connection!!.isClosed) {
            connection!!.close()
        }
        logger.debug { "Conexión con la base de datos cerrada" }
    }

    /**
     * Función para usar la base de datos y cerrarla al finalizar la operación
     * @author Jaime Leon
     * @since 1.0.0
     */
    fun <T> use(block: (DataBaseManager) -> T) {
        try {
            initConexion()
            block(this)
        } catch (e: Exception) {
            logger.error { "Error en la base de datos: ${e.message}" }
        } finally {
            close()
        }
    }

    /**
     * Función para ejecutar un script SQL en la base de datos
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun scriptRunner(reader: Reader, logWriter: Boolean = false) {
        logger.debug { "Ejecutando script SQL con log: $logWriter" }
        val sr = ScriptRunner(connection)
        sr.setLogWriter(if (logWriter) PrintWriter(System.out) else null)
        sr.runScript(reader)
    }
}