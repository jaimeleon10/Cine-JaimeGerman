package org.example.database.service

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.example.database.DatabaseQueries
import org.example.database.Database
import org.example.database.data.initDemoClientes
import org.example.database.data.initDemoLineas
import org.example.database.data.initDemoVentas
import org.lighthousegames.logging.logging
import java.util.*

private val logger = logging()

/**
 * Clase para la base de datos administrada por SqlDeLightManager
 * @property databaseInMemory valor por defecto para crear la base de datos en memoria
 * @property databaseUrl valor por defecto de la url de la base de datos
 * @property databaseInitData valor por defecto para iniciar los datos
 * @property databaseQueries inicio de las queries de la base de datos para su administracion
 * @author Jaime Leon
 * @since 1.0.0
 */
class SqlDeLightManager(
    private val databaseInMemory: Boolean = false,
    private val databaseUrl: String = "jdbc:sqlite:cineJaimeGerman.db",
    private val databaseInitData: Boolean = true
) {
    val databaseQueries: DatabaseQueries by lazy { initQueries() }

    init {
        logger.debug { "Inicializando el gestor de Bases de Datos con SQLDelight" }
        initialize()
    }

    /**
     * Función para iniciar las queries y tablas
     * @author Jaime Leon
     * @since 1.0.0
     * @return DatabaseQueries
     */
    private fun initQueries(): DatabaseQueries {
        return if (databaseInMemory) {
            logger.debug { "SqlDeLightClient - InMemory" }
            JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        } else {
            logger.debug { "SqlDeLightClient - File: $databaseUrl" }
            JdbcSqliteDriver(databaseUrl)
        }.let { driver ->
            // Creamos la base de datos
            logger.debug { "Creando Tablas (si es necesario)" }
            Database.Schema.create(driver)
            Database(driver)
        }.databaseQueries
    }

    /**
     * Función para iniciar datos por defecto
     * @author Jaime Leon
     * @since 1.0.0
     */
    fun initialize() { // Utilizada para realizar un inicio de la base de datos al inicio del programa
        if (databaseInitData) {
            initDataExamples()
        }
    }

    /**
     * Función para borrar todos los datos de las tablas al iniciar los tests
     * @author Jaime Leon
     * @since 1.0.0
     */
    fun initializeTest() {
        if (databaseInitData) {
            databaseQueries.removeAllClientes()
            databaseQueries.removeAllVentas()
            databaseQueries.removeAllLineasVentas()
        }
    }

    /**
     * Función para iniciar los datos de ejemplo
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun initDataExamples() { // Utilizada al principio, desactivado al terminar la versión final del programa
        logger.debug { "Iniciando datos de ejemplo" }
        databaseQueries.transaction {
            //demoClientes()
        }
    }

    /**
     * Función para iniciar datos por defecto para los tests
     * @author Jaime Leon
     * @since 1.0.0
     */
    fun initDataExamplesTest() {
        logger.debug { "Iniciando datos de ejemplo" }
        databaseQueries.transaction {
            demoClientes()
            demoVentas()
            demoLineas()
        }
    }

    /**
     * Función para iniciar datos por defecto de clientes
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun demoClientes() {
        logger.debug { "Datos de ejemplo de Clientes" }
        initDemoClientes().forEach {
            databaseQueries.insertCliente(
                nombre = it.nombre,
                email = it.email,
                numSocio = it.numSocio,
                created_at = it.createdAt.toString(),
                updated_at = it.updatedAt.toString(),
            )
        }
    }

    /**
     * Función para iniciar datos por defecto de ventas
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun demoVentas() {
        logger.debug { "Datos de ejemplo de Ventas" }
        initDemoVentas().forEach {
            databaseQueries.insertVenta(
                id = it.id.toString(),
                cliente_id = it.cliente.id,
                total = it.total,
                fecha_compra = it.fechaCompra.toString(),
                updated_at = it.updatedAt.toString(),
                created_at = it.createdAt.toString(),
            )
        }
    }

    /**
     * Función para iniciar datos por defecto de lineas de venta
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun demoLineas() {
        logger.debug { "Datos de ejemplo de Lineas de venta" }
        initDemoLineas().forEach {
            databaseQueries.insertLineaVenta(
                id = it.id.toString(),
                cantidad = it.cantidad.toLong(),
                producto_id = it.producto.id,
                producto_tipo = it.tipoProducto,
                precio = it.precio,
                venta_id = "67c712fb-5531-4f33-a744-0fdb65cd9dcf",
                created_at = it.createdAt.toString(),
                updated_at = it.updatedAt.toString()
            )
        }
    }
}